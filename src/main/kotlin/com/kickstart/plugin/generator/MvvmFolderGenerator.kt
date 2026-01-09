package com.kickstart.plugin.generator

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.kickstart.plugin.architecture.MvvmStructure

object MvvmFolderGenerator {

    fun generate(project: Project, basePackageDir: VirtualFile) {
        WriteCommandAction.runWriteCommandAction(project) {
            MvvmStructure.directories().forEach { path ->
                createDirs(basePackageDir, path)
            }
        }
    }

    private fun createDirs(root: VirtualFile, relativePath: String) {
        var current = root
        relativePath.split("/").forEach { segment ->
            val existing = current.findChild(segment)
            current = existing ?: current.createChildDirectory(this, segment)
        }
    }
}