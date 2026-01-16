package com.kickstart.plugin.generator

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.kickstart.plugin.dependency.DependencyScope
import com.kickstart.plugin.dependency.DependencyCatalog
import com.kickstart.plugin.version.MavenVersionFetcher
import java.io.File


object DependencyInjector {

    fun addDirect(project: Project, gradleFile: File) {
        WriteCommandAction.runWriteCommandAction(project) {
            val content = gradleFile.readText()

            // 1. Filter dependencies that aren't already there
            val depsToInject = DependencyCatalog.dependencies
                .filterNot { dep -> content.contains("${dep.group}:${dep.name}") }
                .map { dep ->
                    val version =
                        MavenVersionFetcher.fetchLatestRelease(dep.group, dep.name) ?: "1.0.0"
                    val line = when (dep.scope) {
                        DependencyScope.KSP -> "ksp(\"${dep.group}:${dep.name}:$version\")"
                        else -> "implementation(\"${dep.group}:${dep.name}:$version\")"
                    }
                    line
                }

            if (depsToInject.isEmpty()) return@runWriteCommandAction

            // 2. Inject using the robust helper
            val updatedContent = injectIntoDependenciesBlock(content, depsToInject)
            gradleFile.writeText(updatedContent)
        }
    }

    fun addUsingCatalog(project: Project, gradleFile: File) {
        WriteCommandAction.runWriteCommandAction(project) {
            val content = gradleFile.readText()
            val isKts = gradleFile.name.endsWith(".kts")

            val depsToInject = DependencyCatalog.dependencies
                .filterNot { dep ->
                    // Check for alias (handle both libs.name and libs.name.get())
                    val normalizedAlias = dep.alias.replace("-", ".")
                    content.contains(normalizedAlias)
                }
                .map { dep ->
                    val accessor = "libs.${dep.alias.replace("-", ".")}"
                    when (dep.scope) {
                        DependencyScope.KSP -> if (isKts) "ksp($accessor)" else "ksp $accessor"
                        else -> if (isKts) "implementation($accessor)" else "implementation $accessor"
                    }
                }

            if (depsToInject.isEmpty()) return@runWriteCommandAction

            // 3. Inject using the robust helper
            val updatedContent = injectIntoDependenciesBlock(content, depsToInject)
            gradleFile.writeText(updatedContent)
        }
    }

    /**
     * Finds the 'dependencies { ... }' block and injects new lines right at the start.
     * This handles variations in whitespace and KTS/Groovy formatting.
     */
    private fun injectIntoDependenciesBlock(
        content: String,
        newLines: List<String>
    ): String {

        val regex = Regex(
            """dependencies\s*\{([\s\S]*?)\}""",
            RegexOption.MULTILINE
        )

        val match = regex.find(content) ?: return content

        val existingBlock = match.groupValues[1]

        val injection = buildString {
            newLines.forEach {
                append("    ").append(it).append("\n")
            }
        }

        val updatedBlock = "dependencies {\n$injection$existingBlock}"

        return content.replaceRange(
            match.range,
            updatedBlock
        )
    }

}