package com.kickstart.plugin.utils

import com.intellij.openapi.application.ReadAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

object BasePackageDetector {

    fun detect(javaDir: VirtualFile, project: Project): VirtualFile? {
        return ReadAction.compute<VirtualFile?, RuntimeException> {

            // 1️⃣ Try namespace first
            AndroidNamespaceResolver.resolve(project)?.let { namespace ->
                val path = namespace.replace(".", "/")
                javaDir.findFileByRelativePath(path)?.let { return@compute it }
            }

            // 2️⃣ Fallback: folder-based detection (iterative, NOT recursive)
            findFirstPackageIterative(javaDir)
        }
    }

    fun detectPackageName(javaDir: VirtualFile, project: Project): String? {
        return ReadAction.compute<String?, RuntimeException> {

            AndroidNamespaceResolver.resolve(project)?.let { return@compute it }

            findFirstPackageIterative(javaDir)
                ?.path
                ?.substringAfter(javaDir.path + "/")
                ?.replace("/", ".")
        }
    }

    private fun findFirstPackageIterative(root: VirtualFile): VirtualFile? {
        val stack = ArrayDeque<VirtualFile>()
        stack.add(root)

        while (stack.isNotEmpty()) {
            val current = stack.removeFirst()

            val dirs = current.children.filter { it.isDirectory }
            if (dirs.isEmpty()) continue

            // If this directory contains Kotlin/Java files → package root
            if (current.children.any { it.extension == "kt" || it.extension == "java" }) {
                return current
            }

            dirs.forEach { stack.add(it) }
        }

        return null
    }
}
