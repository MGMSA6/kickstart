package com.kickstart.plugin.generator

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

object HiltAppGenerator {

    fun generate(project: Project, basePackageDir: VirtualFile, basePackage: String) {
        WriteCommandAction.runWriteCommandAction(project) {

            // App.kt
            if (basePackageDir.findChild("App.kt") == null) {
                val appFile = basePackageDir.createChildData(this, "App.kt")
                appFile.setBinaryContent("""
                    package $basePackage

                    import android.app.Application
                    import dagger.hilt.android.HiltAndroidApp

                    @HiltAndroidApp
                    class App : Application()
                """.trimIndent().toByteArray())
            }

            // di/AppModule.kt
            val diDir = basePackageDir.findChild("di")
                ?: basePackageDir.createChildDirectory(this, "di")

            if (diDir.findChild("AppModule.kt") == null) {
                val moduleFile = diDir.createChildData(this, "AppModule.kt")
                moduleFile.setBinaryContent("""
                    package $basePackage.di

                    import dagger.Module
                    import dagger.hilt.InstallIn
                    import dagger.hilt.components.SingletonComponent

                    @Module
                    @InstallIn(SingletonComponent::class)
                    object AppModule
                """.trimIndent().toByteArray())
            }
        }
    }
}
