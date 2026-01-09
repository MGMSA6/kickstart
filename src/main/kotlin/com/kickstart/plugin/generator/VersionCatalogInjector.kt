package com.kickstart.plugin.generator

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.kickstart.plugin.dependency.DependencyResolver
import com.kickstart.plugin.dependency.MvvmDependencyCatalog
import java.io.File

object VersionCatalogInjector {

    fun addMvvmDependencies(project: Project, catalogFile: File) {
        WriteCommandAction.runWriteCommandAction(project) {
            val content = catalogFile.readText()

            val librariesIndex = content.indexOf("[libraries]")
            if (librariesIndex == -1) return@runWriteCommandAction

            val insertPos = content.length
            val builder = StringBuilder(content)

            MvvmDependencyCatalog.dependencies.forEach { dep ->
                if (!content.contains(dep.alias)) {
                    builder.append(
                        "\n${dep.alias} = { group = \"${dep.group}\", name = \"${dep.name}\" }"
                    )
                }
            }

            catalogFile.writeText(builder.toString())
        }
    }
}