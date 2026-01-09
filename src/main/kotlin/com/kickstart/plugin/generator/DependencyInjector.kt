package com.kickstart.plugin.generator

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.kickstart.plugin.dependency.DependencyResolver
import java.io.File


object DependencyInjector {

    fun addMvvmDependencies(project: Project, gradleFile: File) {
        val resolvedDeps = DependencyResolver.resolveMvvm()
        if (resolvedDeps.isEmpty()) return

        WriteCommandAction.runWriteCommandAction(project) {
            val content = gradleFile.readText()
            if (!content.contains("dependencies")) return@runWriteCommandAction

            val isKotlinDsl = gradleFile.name.endsWith(".kts")

            val newLines = resolvedDeps
                .filterNot { dep ->
                    content.contains("${dep.group}:${dep.artifact}")
                }
                .map { dep ->
                    if (isKotlinDsl)
                        """implementation("${dep.group}:${dep.artifact}:${dep.version}")"""
                    else
                        """implementation "${dep.group}:${dep.artifact}:${dep.version}""""
                }

            if (newLines.isEmpty()) return@runWriteCommandAction

            val updated = content.replace(
                "dependencies {",
                buildString {
                    append("dependencies {\n")
                    newLines.forEach { append("    $it\n") }
                }
            )

            gradleFile.writeText(updated)
        }
    }
}