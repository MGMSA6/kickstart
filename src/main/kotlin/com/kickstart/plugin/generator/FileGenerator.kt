package com.kickstart.plugin.generator

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

object FileGenerator {

    fun createKotlinFile(
        project: Project,
        dir: VirtualFile,
        fileName: String,
        content: String
    ) {
        val file = dir.findChild(fileName)
            ?: dir.createChildData(project, fileName)

        file.setBinaryContent(content.toByteArray())
    }
}
