package com.kickstart.plugin.generator

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.kickstart.plugin.dependency.DependencyScope
import com.kickstart.plugin.dependency.MvvmDependencyCatalog
import java.io.File


object DependencyInjector {

    fun addUsingCatalog(project: Project, gradle: File) {
        WriteCommandAction.runWriteCommandAction(project) {
            val content = gradle.readText()
            if (!content.contains("dependencies")) return@runWriteCommandAction

            val isKts = gradle.name.endsWith(".kts")

            val newLines = MvvmDependencyCatalog.dependencies
                .filterNot { dep ->
                    // avoid duplicates
                    content.contains(dep.alias.replace("-", "."))
                }
                .map { dep ->
                    val accessor = "libs.${dep.alias.replace("-", ".")}"

                    when (dep.scope) {
                        DependencyScope.KSP -> {
                            if (isKts) "ksp($accessor)" else "ksp $accessor"
                        }

                        DependencyScope.IMPLEMENTATION -> {
                            if (isKts) "implementation($accessor)" else "implementation $accessor"
                        }
                    }
                }

            if (newLines.isEmpty()) return@runWriteCommandAction

            gradle.writeText(
                content.replace(
                    "dependencies {",
                    buildString {
                        append("dependencies {\n")
                        newLines.forEach { append("    $it\n") }
                    }
                )
            )
        }
    }
}
