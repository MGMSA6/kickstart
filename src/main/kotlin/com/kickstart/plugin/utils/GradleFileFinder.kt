package com.kickstart.plugin.utils

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import java.io.File

import com.intellij.openapi.vfs.LocalFileSystem


object GradleFileFinder {

    fun findModuleGradleFile(project: Project): File? {
        val basePath = project.basePath ?: return null

        val root: VirtualFile =
            LocalFileSystem.getInstance()
                .findFileByPath(basePath)
                ?: return null

        // 1️⃣ Single-module project (root)
        findGradleFileIn(root)?.let { return it }

        // 2️⃣ Multi-module project (app / other modules)
        root.children
            .filter { it.isDirectory }
            .forEach { dir ->
                findGradleFileIn(dir)?.let { return it }
            }

        return null
    }

    private fun findGradleFileIn(dir: VirtualFile): File? {
        return listOf(
            dir.findChild("build.gradle.kts"),
            dir.findChild("build.gradle")
        ).firstOrNull { it != null }
            ?.let { File(it.path) }
    }
}

