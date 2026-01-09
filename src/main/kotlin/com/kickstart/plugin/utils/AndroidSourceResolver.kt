package com.kickstart.plugin.utils

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import java.io.File

object AndroidSourceResolver {

    fun findMainJavaDir(project: Project): VirtualFile? {
        val basePath = project.basePath ?: return null

        val possiblePaths = listOf(
            "$basePath/app/src/main/java",
            "$basePath/src/main/java"
        )

        for (path in possiblePaths) {
            val file = File(path)
            if (file.exists()) {
                return LocalFileSystem.getInstance()
                    .refreshAndFindFileByIoFile(file)
            }
        }
        return null
    }
}