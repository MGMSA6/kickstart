package com.kickstart.plugin.utils

import com.intellij.openapi.application.ReadAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile


object AndroidProjectDetector {

    fun isAndroidProject(project: Project): Boolean {
        val basePath = project.basePath ?: return false
        val baseDir =
            LocalFileSystem.getInstance().findFileByPath(basePath) ?: return false

        return containsAndroidGradlePlugin(baseDir)
    }

    private fun containsAndroidGradlePlugin(root: VirtualFile): Boolean {
        return ReadAction.compute<Boolean, RuntimeException> {
            val stack = ArrayDeque<VirtualFile>()
            stack.addLast(root)

            while (stack.isNotEmpty()) {
                val file = stack.removeLast()

                if (file.isDirectory) {
                    file.children.forEach { stack.addLast(it) }
                } else if (
                    file.name == "build.gradle" ||
                    file.name == "build.gradle.kts"
                ) {
                    val content = String(file.contentsToByteArray())
                    if (
                        content.contains("com.android.application") ||
                        content.contains("com.android.library")
                    ) {
                        return@compute true
                    }
                }
            }
            false
        }
    }
}