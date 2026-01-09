package com.kickstart.plugin.utils

import com.intellij.openapi.project.Project
import java.io.File


object VersionCatalogDetector {

    fun findCatalog(project: Project): File? {
        val base = project.basePath ?: return null
        val catalog = File("$base/gradle/libs.versions.toml")
        return if (catalog.exists()) catalog else null
    }
}