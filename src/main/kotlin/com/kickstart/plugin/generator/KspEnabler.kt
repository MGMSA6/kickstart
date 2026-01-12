package com.kickstart.plugin.generator

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import java.io.File

object KspEnabler {

    fun ensureEnabled(project: Project, gradle: File) {
        WriteCommandAction.runWriteCommandAction(project) {
            val content = gradle.readText()

            if (content.contains("com.google.devtools.ksp")) return@runWriteCommandAction

            val updated = content.replace(
                "plugins {",
                """
                plugins {
                    id("com.google.devtools.ksp")
                """.trimIndent()
            )

            gradle.writeText(updated)
        }
    }
}