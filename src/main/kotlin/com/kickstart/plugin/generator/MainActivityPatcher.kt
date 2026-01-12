package com.kickstart.plugin.generator

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.openapi.vfs.VirtualFile

object MainActivityPatcher {

    fun patch(project: Project, basePackageDir: VirtualFile) {
        val mainActivity = findMainActivity(basePackageDir) ?: return

        WriteCommandAction.runWriteCommandAction(project) {
            val content = mainActivity.inputStream.reader().readText()

            if (content.contains("@AndroidEntryPoint")) return@runWriteCommandAction

            val updated = buildString {
                append("import dagger.hilt.android.AndroidEntryPoint\n\n")
                append(
                    content.replace(
                        "class MainActivity",
                        "@AndroidEntryPoint\nclass MainActivity"
                    )
                )
            }

            mainActivity.setBinaryContent(updated.toByteArray())
        }
    }

    fun findMainActivity(root: VirtualFile): VirtualFile? {
        var result: VirtualFile? = null

        VfsUtilCore.iterateChildrenRecursively(
            root,
            null
        ) { file ->
            if (file.name == "MainActivity.kt") {
                result = file
                false // stop traversal
            } else {
                true
            }
        }
        return result
    }


}
