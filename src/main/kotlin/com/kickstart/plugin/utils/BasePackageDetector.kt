package com.kickstart.plugin.utils

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

object BasePackageDetector {

    fun detect(javaDir: VirtualFile, project: Project): VirtualFile? {
        val namespace = AndroidNamespaceResolver.resolve(project) ?: return null
        val relativePath = namespace.replace(".", "/")

        return javaDir.findFileByRelativePath(relativePath)
    }

    fun detectPackageName(project: Project): String? {
        return AndroidNamespaceResolver.resolve(project)
    }
}
