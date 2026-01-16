package com.kickstart.plugin.generator

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.kickstart.plugin.architecture.ArchitectureStructureProvider
import com.kickstart.plugin.architecture.ArchitectureType

object FolderGenerator {

    fun generate(project: Project, basePackageDir: VirtualFile, type: ArchitectureType) {
        WriteCommandAction.runWriteCommandAction(project) {
            // Get the directory list dynamically based on the selected type
            val directories = ArchitectureStructureProvider.getDirectories(type)

            directories.forEach { path ->
                createDirectoryTree(basePackageDir, path)
            }
        }
    }

    private fun createDirectoryTree(root: VirtualFile, relativePath: String) {
        var current = root
        relativePath.split("/").forEach { segment ->
            // Use findChild to avoid duplicates, or create if missing
            current = current.findChild(segment) ?: current.createChildDirectory(this, segment)
        }
    }
}