package com.kickstart.plugin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import com.kickstart.plugin.generator.DependencyInjector
import com.kickstart.plugin.generator.HiltAppGenerator
import com.kickstart.plugin.generator.KspEnabler
import com.kickstart.plugin.generator.MvvmFolderGenerator
import com.kickstart.plugin.generator.VersionCatalogInjector
import com.kickstart.plugin.ui.KickstartWizardDialog
import com.kickstart.plugin.utils.AndroidModuleDetector
import com.kickstart.plugin.utils.AndroidSourceResolver
import com.kickstart.plugin.utils.BasePackageDetector
import com.kickstart.plugin.utils.GradleFileFinder
import com.kickstart.plugin.utils.VersionCatalogDetector

class KickstartAction : AnAction("Kickstart") {

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return

        // 1. Show wizard
        val dialog = KickstartWizardDialog()
        dialog.show()
        if (!dialog.isOK) return

        // 2. Locate src/main/java
        val javaDir = AndroidSourceResolver.findMainJavaDir(project)
        if (javaDir == null) {
            Messages.showErrorDialog(
                project,
                "Kickstart supports Android projects only.\n" +
                        "Could not find src/main/java directory.",
                "Kickstart"
            )
            return
        }

        // 3. Detect base package
        val basePackageDir = BasePackageDetector.detect(javaDir, project)
        if (basePackageDir == null) {
            Messages.showErrorDialog(
                project,
                "Could not detect base package under src/main/java.",
                "Kickstart"
            )
            return
        }

        // 4. Generate MVVM folders
        MvvmFolderGenerator.generate(project, basePackageDir)

        // 4.1 Generate App.kt + Hilt setup
        val basePackage = BasePackageDetector.detectPackageName(project)
            ?: return
        HiltAppGenerator.generate(
            project = project,
            basePackageDir = basePackageDir,
            basePackage = basePackage
        )

        // 4.1 Generate App.kt + Hilt setup
        val appModules = AndroidModuleDetector.findAppModules(project)

        if (appModules.isEmpty()) {
            Messages.showErrorDialog(
                project,
                "No Android application modules found.",
                "Kickstart"
            )
            return
        }

        // 5. Dependency handling
        val versionCatalog = VersionCatalogDetector.findCatalog(project)
        val gradleFile = GradleFileFinder.findAppGradleFile(project)

        if (gradleFile == null) {
            Messages.showWarningDialog(
                project,
                "MVVM folders were created, but app/build.gradle(.kts) was not found.\n" +
                        "Please add dependencies manually.",
                "Kickstart"
            )
            return
        }

        if (versionCatalog != null) {
            // Modern setup: Version Catalog + KSP
            VersionCatalogInjector.inject(project, versionCatalog)
            KspEnabler.ensureEnabled(project, gradleFile)
            DependencyInjector.addUsingCatalog(project, gradleFile)
        } else {
            // (Optional) future: normal Gradle dependency injection
            DependencyInjector.addUsingCatalog(project, gradleFile)
        }

        Messages.showInfoMessage(
            project,
            "MVVM setup completed successfully ✅\n" +
                    "Lifecycle, Room, Coroutines & Hilt configured using KSP.",
            "Kickstart"
        )
    }
}
