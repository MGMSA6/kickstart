package com.kickstart.plugin.utils

import com.intellij.openapi.vfs.VirtualFile

object BasePackageDetector {

    fun detect(javaDir: VirtualFile): VirtualFile? {
        // pick first package directory (com/xxx/yyy)
        return javaDir.children
            .firstOrNull { it.isDirectory }
    }
}