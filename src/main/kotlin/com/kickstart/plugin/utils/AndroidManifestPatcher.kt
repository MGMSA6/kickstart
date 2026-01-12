package com.kickstart.plugin.utils

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

object AndroidManifestPatcher {

    fun patch(
        project: Project,
        appModuleDir: VirtualFile,
        packageName: String
    ) {
        val manifest = appModuleDir
            .findFileByRelativePath("src/main/AndroidManifest.xml")
            ?: return

        WriteCommandAction.runWriteCommandAction(project) {
            val xml = manifest.inputStream.reader().readText()

            if (xml.contains("android:name=")) return@runWriteCommandAction

            val updated = xml.replace(
                "<application",
                "<application\n        android:name=\".${
                    "App"
                }\""
            )

            manifest.setBinaryContent(updated.toByteArray())
        }
    }
}
