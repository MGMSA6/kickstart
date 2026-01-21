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

            content = insertIntoSection(
                content,
                "plugins",
                buildString {
                    appendCommentOnce(content, "# Plugins\n")

                    injectPluginAlias(
                        content,
                        this,
                        alias = "ksp",
                        id = "com.google.devtools.ksp",
                        versionKey = "ksp"
                    )

                    injectPluginAlias(
                        content,
                        this,
                        alias = "hilt-android",
                        id = "com.google.dagger.hilt.android",
                        versionKey = "hilt"
                    )

                    injectPluginAlias(
                        content,
                        this,
                        alias = "kotlin-android",
                        id = "org.jetbrains.kotlin.android",
                        versionKey = "kotlin"
                    )
                }
            )

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

    private fun injectPluginAlias(
        content: String,
        builder: StringBuilder,
        alias: String,
        id: String,
        versionKey: String
    ) {
        if (!isPluginAliasPresent(content, alias)) {
            builder.append(
                "$alias = { id = \"$id\", version.ref = \"$versionKey\" }\n"
            )
        }
    }

    private fun isPluginAliasPresent(content: String, alias: String): Boolean {
        val pluginsStart = content.indexOf("[plugins]")
        if (pluginsStart == -1) return false

        val nextSectionStart = content.indexOf("\n[", pluginsStart + 1)
        val pluginsSection = if (nextSectionStart != -1) {
            content.substring(pluginsStart, nextSectionStart)
        } else {
            content.substring(pluginsStart)
        }

        return pluginsSection.contains("$alias =")
    }


}