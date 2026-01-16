package com.kickstart.plugin.generator

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.kickstart.plugin.dependency.DependencyResolver
import com.kickstart.plugin.dependency.DependencyCatalog
import com.kickstart.plugin.dependency.VersionKeyResolver
import com.kickstart.plugin.version.MavenVersionFetcher
import java.io.File

object VersionCatalogInjector {

    fun inject(project: Project, catalog: File) {
        val original = catalog.readText()

        val versions = DependencyResolver.resolveLatestVersions()
        println("Resolved versions = $versions")

        WriteCommandAction.runWriteCommandAction(project) {
            var content = original

            // ---------- [versions] ----------
            content = ensureSection(content, "versions")

            content = insertIntoSection(
                content,
                "versions",
                buildString {
                    appendCommentOnce(content, "# Dependency versions\n")

                    versions.forEach { (key, version) ->
                        if (!content.contains("$key =")) {
                            append("$key = \"$version\"\n")
                        }
                    }
                }
            )

            // ---------- [libraries] ----------
            content = ensureSection(content, "libraries")

            content = insertIntoSection(
                content,
                "libraries",
                buildString {
                    appendCommentOnce(content, "# Libraries\n")

                    DependencyCatalog.dependencies.forEach { dep ->
                        val versionKey = VersionKeyResolver.versionKeyFor(dep)

                        if (!content.contains("${dep.alias} =")) {
                            append(
                                "${dep.alias} = { " +
                                        "group = \"${dep.group}\", " +
                                        "name = \"${dep.name}\", " +
                                        "version.ref = \"$versionKey\" " +
                                        "}\n"
                            )
                        }
                    }
                }
            )

            // ---------- [plugins] ----------
            content = ensureSection(content, "plugins")

            if (!content.contains("ksp =")) {
                val kspVersion = MavenVersionFetcher.fetchLatestRelease(
                    "com.google.devtools.ksp",
                    "symbol-processing-api"
                )

                if (kspVersion != null) {
                    content = insertIntoSection(
                        content,
                        "plugins",
                        buildString {
                            appendCommentOnce(content, "# Code generation\n")
                            append(
                                "ksp = { id = \"com.google.devtools.ksp\", version = \"$kspVersion\" }\n"
                            )
                        }
                    )
                }
            }

            catalog.writeText(content)
        }
    }

    // ------------------------------------------------------------------------
    // Helpers
    // ------------------------------------------------------------------------

    private fun ensureSection(content: String, section: String): String {
        return if (content.contains("[$section]")) {
            content
        } else {
            "$content\n[$section]\n"
        }
    }

    private fun insertIntoSection(
        content: String,
        section: String,
        toInsert: String
    ): String {
        if (toInsert.isBlank()) return content

        val sectionHeader = "[$section]"
        val startIndex = content.indexOf(sectionHeader)
        if (startIndex == -1) return content

        val insertPos = content.indexOf('\n', startIndex) + 1

        return content.take(insertPos) +
                toInsert +
                content.substring(insertPos)
    }

    private fun StringBuilder.appendCommentOnce(
        content: String,
        comment: String
    ) {
        if (!content.contains(comment.trim())) {
            append(comment)
        }
    }
}