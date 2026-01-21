package com.kickstart.plugin.utils

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import java.io.File
import com.intellij.openapi.vfs.LocalFileSystem


object GradleFileFinder {

    fun findAppModuleGradle(project: Project): File? {
        val basePath = project.basePath ?: return null
        val root = LocalFileSystem.getInstance().findFileByPath(basePath) ?: return null

        // 1️⃣ Look for app module explicitly (Android standard)
        root.findChild("app")?.let { appDir ->
            findGradleFileIn(appDir)?.let { return it }
        }

        // 2️⃣ Fallback: scan first-level modules
        root.children
            .filter { it.isDirectory }
            .forEach { dir ->
                findGradleFileIn(dir)?.let { return it }
            }

        return null
    }

    private fun findGradleFileIn(dir: VirtualFile): File? {
        val gradleFile =
            dir.findChild("build.gradle.kts")
                ?: dir.findChild("build.gradle")

        return gradleFile?.let { File(it.path) }
    }
}

