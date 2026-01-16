package com.kickstart.plugin.generator

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

object MVVMCoreGenerator {

    fun generate(
        project: Project,
        basePackage: String,
        basePackageDir: VirtualFile
    ) {
        WriteCommandAction.runWriteCommandAction(project) {

            val utilDir = basePackageDir.getOrCreate("util")
            val dataDir = basePackageDir.getOrCreate("data")
            val remoteDir = dataDir.getOrCreate("remote")
            val interceptorDir = remoteDir.getOrCreate("interceptor")
            val apiDir = remoteDir.getOrCreate("api")
            val diDir = basePackageDir.getOrCreate("di")

            // ---------- UTIL ----------

            FileGenerator.createKotlinFile(
                project,
                utilDir,
                "ApiConstants.kt",
                """
                package $basePackage.util

                object ApiConstants {
                    const val BASE_URL = "https://api.example.com/"
                    const val CONNECT_TIMEOUT = 30L
                    const val READ_TIMEOUT = 30L
                }
                """.trimIndent()
            )

            FileGenerator.createKotlinFile(
                project,
                utilDir,
                "NetworkResult.kt",
                """
                package $basePackage.util

                sealed class NetworkResult<out T> {
                    data class Success<T>(val data: T) : NetworkResult<T>()
                    data class Error(val message: String, val code: Int? = null) : NetworkResult<Nothing>()
                    object Loading : NetworkResult<Nothing>()
                }
                """.trimIndent()
            )

            FileGenerator.createKotlinFile(
                project,
                utilDir,
                "ErrorHandler.kt",
                """
                package $basePackage.util

                import retrofit2.HttpException
                import java.io.IOException

                object ErrorHandler {
                    fun resolve(t: Throwable): NetworkResult.Error =
                        when (t) {
                            is HttpException -> NetworkResult.Error(t.message(), t.code())
                            is IOException -> NetworkResult.Error("Network error")
                            else -> NetworkResult.Error("Unknown error")
                        }
                }
                """.trimIndent()
            )

            FileGenerator.createKotlinFile(
                project,
                utilDir,
                "HeaderProvider.kt",
                """
                package $basePackage.util

                interface HeaderProvider {
                    fun headers(): Map<String, String>
                }

                class DefaultHeaderProvider : HeaderProvider {
                    override fun headers(): Map<String, String> =
                        mapOf("Content-Type" to "application/json")
                }
                """.trimIndent()
            )

            // ---------- INTERCEPTOR ----------

            FileGenerator.createKotlinFile(
                project,
                interceptorDir,
                "LoggingInterceptor.kt",
                """
                package $basePackage.data.remote.interceptor

                import okhttp3.Interceptor
                import okhttp3.Response
                import android.util.Log

                class LoggingInterceptor : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val request = chain.request()
                        Log.d("HTTP", "\${'$'}{request.method} \${'$'}{request.url}")
                        return chain.proceed(request)
                    }
                }
                """.trimIndent()
            )

            // ---------- NETWORK CLIENT ----------

            FileGenerator.createKotlinFile(
                project,
                remoteDir,
                "NetworkClient.kt",
                """
                package $basePackage.data.remote

                import okhttp3.OkHttpClient
                import retrofit2.Retrofit
                import retrofit2.converter.gson.GsonConverterFactory
                import java.util.concurrent.TimeUnit
                import $basePackage.util.ApiConstants
                import $basePackage.data.remote.interceptor.LoggingInterceptor

                object NetworkClient {

                    fun create(): Retrofit {
                        val client = OkHttpClient.Builder()
                            .addInterceptor(LoggingInterceptor())
                            .connectTimeout(ApiConstants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(ApiConstants.READ_TIMEOUT, TimeUnit.SECONDS)
                            .build()

                        return Retrofit.Builder()
                            .baseUrl(ApiConstants.BASE_URL)
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                    }
                }
                """.trimIndent()
            )

            // ---------- API SERVICE ----------

            FileGenerator.createKotlinFile(
                project,
                apiDir,
                "ApiService.kt",
                """
                package $basePackage.data.remote.api

                import retrofit2.http.GET

                interface ApiService {
                    @GET("health")
                    suspend fun health(): String
                }
                """.trimIndent()
            )

            // ---------- DI ----------

            FileGenerator.createKotlinFile(
                project,
                diDir,
                "NetworkModule.kt",
                """
                package $basePackage.di

                import dagger.Module
                import dagger.hilt.InstallIn
                import dagger.hilt.components.SingletonComponent
                import dagger.Provides
                import javax.inject.Singleton
                import retrofit2.Retrofit
                import $basePackage.data.remote.NetworkClient
                import $basePackage.data.remote.api.ApiService

                @Module
                @InstallIn(SingletonComponent::class)
                object NetworkModule {

                    @Provides
                    @Singleton
                    fun provideRetrofit(): Retrofit =
                        NetworkClient.create()

                    @Provides
                    @Singleton
                    fun provideApiService(retrofit: Retrofit): ApiService =
                        retrofit.create(ApiService::class.java)
                }
                """.trimIndent()
            )
        }
    }

    private fun VirtualFile.getOrCreate(name: String): VirtualFile =
        findChild(name) ?: createChildDirectory(this, name)
}
