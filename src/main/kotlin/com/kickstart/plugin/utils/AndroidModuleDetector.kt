package com.kickstart.plugin.utils

import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.VirtualFile

object AndroidModuleDetector {

    fun findAppModules(project: Project): List<VirtualFile> {
        val modules = mutableListOf<VirtualFile>()

        ProjectRootManager.getInstance(project)
            .contentRoots
            .forEach { root ->
                root.findFileByRelativePath("src/main/AndroidManifest.xml")
                    ?.let { modules.add(root) }
            }

        return modules
    }
}
