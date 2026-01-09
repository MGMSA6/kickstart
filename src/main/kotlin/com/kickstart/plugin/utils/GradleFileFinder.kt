package com.kickstart.plugin.utils

import com.intellij.openapi.project.Project
import java.io.File


object GradleFileFinder {

    /**
     * Finds app module build.gradle or build.gradle.kts
     */
    fun findAppGradleFile(project: Project): File? {
        val basePath = project.basePath ?: return null

        val candidates = listOf(
            "$basePath/app/build.gradle.kts",
            "$basePath/app/build.gradle"
        )

        return candidates
            .map { File(it) }
            .firstOrNull { it.exists() }
    }
}