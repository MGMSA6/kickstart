package com.kickstart.plugin.generator

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import java.io.File

object KspEnabler {

    fun ensureEnabled(project: Project, gradleFile: File) {
        WriteCommandAction.runWriteCommandAction(project) {
            val content = gradleFile.readText()

            if (content.contains("libs.plugins.ksp")) return@runWriteCommandAction

            val updated = content.replace(
                Regex("""plugins\s*\{"""),
                """
                plugins {
                    alias(libs.plugins.ksp)
                """.trimIndent()
            )

            gradleFile.writeText(updated)
        }
    }
}
