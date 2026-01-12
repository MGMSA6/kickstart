package com.kickstart.plugin.utils

import com.intellij.openapi.project.Project

object AndroidNamespaceResolver {

    fun resolve(project: Project): String? {
        val gradleFile = GradleFileFinder.findAppGradleFile(project) ?: return null
        val content = gradleFile.readText()

        // namespace = "com.example.demo"
        Regex("""namespace\s*=\s*["'](.+?)["']""")
            .find(content)
            ?.let { return it.groupValues[1] }

        // fallback: applicationId
        Regex("""applicationId\s*=\s*["'](.+?)["']""")
            .find(content)
            ?.let { return it.groupValues[1] }

        return null
    }
}
