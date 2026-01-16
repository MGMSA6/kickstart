package com.kickstart.plugin.generator

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

object MvpCoreGenerator {

    fun generate(
        project: Project,
        basePackage: String,
        basePackageDir: VirtualFile
    ) {
        WriteCommandAction.runWriteCommandAction(project) {
            val utilDir = basePackageDir.getOrCreate("util")
            val coreBaseDir = basePackageDir.getOrCreate("core/base")
            val remoteDir = basePackageDir.getOrCreate("data/remote")
            val diDir = basePackageDir.getOrCreate("di")

            // ---------- CORE BASE (The heart of MVP) ----------

            FileGenerator.createKotlinFile(project, coreBaseDir, "BaseView.kt", """
                package $basePackage.core.base

                interface BaseView {
                    fun showLoading()
                    fun hideLoading()
                    fun onError(message: String)
                }
            """.trimIndent())

            FileGenerator.createKotlinFile(project, coreBaseDir, "BasePresenter.kt", """
                package $basePackage.core.base

                interface BasePresenter<V : BaseView> {
                    fun attachView(view: V)
                    fun detachView()
                    val isViewAttached: Boolean
                }
            """.trimIndent())

            // ---------- UTIL (Result Handling) ----------

            FileGenerator.createKotlinFile(project, utilDir, "NetworkResult.kt", """
                package $basePackage.util

                sealed class NetworkResult<out T> {
                    data class Success<T>(val data: T) : NetworkResult<T>()
                    data class Error(val message: String, val code: Int? = null) : NetworkResult<Nothing>()
                }
            """.trimIndent())

            // ---------- NETWORK CLIENT (Data Layer) ----------

            val interceptorDir = remoteDir.getOrCreate("interceptor")
            FileGenerator.createKotlinFile(project, remoteDir, "NetworkClient.kt", """
                package $basePackage.data.remote

                import okhttp3.OkHttpClient
                import retrofit2.Retrofit
                import retrofit2.converter.gson.GsonConverterFactory
                import $basePackage.data.remote.interceptor.LoggingInterceptor

                object NetworkClient {
                    fun create(): Retrofit = Retrofit.Builder()
                        .baseUrl("https://api.example.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(OkHttpClient.Builder().addInterceptor(LoggingInterceptor()).build())
                        .build()
                }
            """.trimIndent())

            FileGenerator.createKotlinFile(project, interceptorDir, "LoggingInterceptor.kt", """
                package $basePackage.data.remote.interceptor
                import okhttp3.Interceptor
                import okhttp3.Response
                import android.util.Log

                class LoggingInterceptor : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val request = chain.request()
                        Log.d("MVP-Network", "${'$'}{request.method} ${'$'}{request.url}")
                        return chain.proceed(request)
                    }
                }
            """.trimIndent())

            // ---------- DI (Dependency Injection) ----------

            FileGenerator.createKotlinFile(project, diDir, "NetworkModule.kt", """
                package $basePackage.di

                import dagger.Module
                import dagger.Provides
                import dagger.hilt.InstallIn
                import dagger.hilt.components.SingletonComponent
                import retrofit2.Retrofit
                import javax.inject.Singleton
                import $basePackage.data.remote.NetworkClient

                @Module
                @InstallIn(SingletonComponent::class)
                object NetworkModule {
                    @Provides
                    @Singleton
                    fun provideRetrofit(): Retrofit = NetworkClient.create()
                }
            """.trimIndent())

            // ---------- EXAMPLE CONTRACT (Template for Developers) ----------

            val featureDir = basePackageDir.getOrCreate("presentation/feature")
            FileGenerator.createKotlinFile(project, featureDir, "FeatureContract.kt", """
                package $basePackage.presentation.feature
                
                import $basePackage.core.base.BaseView
                import $basePackage.core.base.BasePresenter

                interface FeatureContract {
                    interface View : BaseView {
                        fun onDataLoaded(data: String)
                    }

                    interface Presenter : BasePresenter<View> {
                        fun loadData()
                    }
                }
            """.trimIndent())
        }
    }

    private fun VirtualFile.getOrCreate(path: String): VirtualFile {
        var current = this
        path.split("/").forEach { segment ->
            current = current.findChild(segment) ?: current.createChildDirectory(this, segment)
        }
        return current
    }
}