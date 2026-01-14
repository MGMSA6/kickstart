package com.kickstart.plugin.utils

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import java.io.File

object GradleFileFinder {

    fun findAppGradleFile(moduleRoot: VirtualFile): File? {
        return listOf(
            "build.gradle.kts",
            "build.gradle"
        ).firstNotNullOfOrNull { moduleRoot.findChild(it) }
            ?.let { File(it.path) }
    }

    // (Optional) fallback for old usage
    fun findAppGradleFile(project: Project): File? {
        val baseDir = project.baseDir ?: return null
        return findAppGradleFile(baseDir)
    }
}
