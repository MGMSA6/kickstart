package com.kickstart.plugin.utils

import com.intellij.openapi.externalSystem.importing.ImportSpecBuilder
import com.intellij.openapi.externalSystem.util.ExternalSystemUtil
import com.intellij.openapi.externalSystem.model.ProjectSystemId
import com.intellij.openapi.project.Project

object GradleUtil {
    private val GRADLE_SYSTEM_ID = ProjectSystemId("GRADLE")
    fun refreshProject(project: Project) {
        val spec = ImportSpecBuilder(project, GRADLE_SYSTEM_ID)
            .usePreviewMode()
            .build()

        ExternalSystemUtil.refreshProjects(spec)
    }
}