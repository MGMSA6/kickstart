package com.kickstart.plugin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.kickstart.plugin.architecture.ArchitectureType
import com.kickstart.plugin.generator.*
import com.kickstart.plugin.ui.KickstartWizardDialog
import com.kickstart.plugin.utils.*

class KickstartAction : AnAction("Kickstart") {

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return

        // 1. Show wizard and get user selection
        val dialog = KickstartWizardDialog()
        dialog.show()
        if (!dialog.isOK) return

        // Extract selected architecture from dialog
        val selectedArchitecture = dialog.getSelectedArchitecture()

        // 2. Resolve Environment
        val javaDir = AndroidSourceResolver.findMainJavaDir(project)
        val basePackageDir = javaDir?.let { BasePackageDetector.detect(it, project) }
        val basePackage = javaDir?.let { BasePackageDetector.detectPackageName(it, project) }

        if (javaDir == null || basePackageDir == null || basePackage == null) {
            Messages.showErrorDialog(
                project,
                "Kickstart support requires a valid Android project structure (src/main/java).",
                "Initialization Error"
            )
            return
        }

        // 3. Folder Generation (Generic)
        FolderGenerator.generate(project, basePackageDir, selectedArchitecture)

        // 4. Boilerplate Generation (Hilt, Network, Core)
        // These generators should ideally be updated to adapt to 'selectedArchitecture' if needed
        HiltAppGenerator.generate(
            project = project,
            basePackageDir = basePackageDir,
            basePackage = basePackage
        )

        when(selectedArchitecture) {
            ArchitectureType.MVVM -> {
                MVVMCoreGenerator.generate(
                    project = project,
                    basePackage = basePackage,
                    basePackageDir = basePackageDir
                )
            }
            ArchitectureType.MVP -> {
                MvpCoreGenerator.generate(project, basePackage, basePackageDir)
            }

            ArchitectureType.MVI -> {
                MviCoreGenerator.generate(project, basePackage, basePackageDir)
            }
        }

        // 5. Build Configuration (Dependency Injection)
        handleDependencies(project, selectedArchitecture.displayName)
    }

    private fun handleDependencies(project: Project, archName: String) {
        val versionCatalog = VersionCatalogDetector.findCatalog(project)
        val gradleFile = GradleFileFinder.findModuleGradleFile(project)

        if (gradleFile == null) {
            Messages.showWarningDialog(
                project,
                "$archName folders were created, but app/build.gradle(.kts) was not found. Please add dependencies manually.",
                "Gradle Not Found"
            )
            return
        }

        if (versionCatalog != null) {
            VersionCatalogInjector.inject(project, versionCatalog)
            KspEnabler.ensureEnabled(project, gradleFile)
            DependencyInjector.addUsingCatalog(project, gradleFile)
        } else {
            DependencyInjector.addDirect(project, gradleFile)
        }

        Messages.showInfoMessage(
            project,
            "$archName setup completed successfully ✅\n" +
                    "Hilt, Network, and Core utilities configured.",
            "Kickstart Success"
        )
    }
}