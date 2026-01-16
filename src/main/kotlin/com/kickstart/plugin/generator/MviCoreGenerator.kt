package com.kickstart.plugin.generator

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

object MviCoreGenerator {

    fun generate(
        project: Project,
        basePackage: String,
        basePackageDir: VirtualFile
    ) {
        WriteCommandAction.runWriteCommandAction(project) {
            val mviCoreDir = basePackageDir.getOrCreate("core/mvi")
            val commonDir = basePackageDir.getOrCreate("core/common")
            val utilDir = basePackageDir.getOrCreate("util")
            val diDir = basePackageDir.getOrCreate("di")

            // ---------- MVI CORE INTERFACES ----------

            FileGenerator.createKotlinFile(project, mviCoreDir, "MviInterfaces.kt", """
                package $basePackage.core.mvi

                interface UiState   // Represents the current state of the UI
                interface UiIntent  // Represents user actions (e.g., Refresh, Click)
                interface UiEffect  // Represents one-time side effects (e.g., Navigation, Toast)
            """.trimIndent())

            // ---------- BASE MVI VIEWMODEL ----------

            FileGenerator.createKotlinFile(project, mviCoreDir, "BaseMviViewModel.kt", """
                package $basePackage.core.mvi

                import androidx.lifecycle.ViewModel
                import androidx.lifecycle.viewModelScope
                import kotlinx.coroutines.flow.*
                import kotlinx.coroutines.launch

                abstract class BaseMviViewModel<I : UiIntent, S : UiState, E : UiEffect>(
                    initialState: S
                ) : ViewModel() {

                    private val _uiState = MutableStateFlow(initialState)
                    val uiState = _uiState.asStateFlow()

                    private val _effect = MutableSharedFlow<E>()
                    val effect = _effect.asSharedFlow()

                    /**
                     * Handle user intents
                     */
                    abstract fun onIntent(intent: I)

                    /**
                     * Update the UI state safely
                     */
                    protected fun setState(reducer: S.() -> S) {
                        _uiState.update { it.reducer() }
                    }

                    /**
                     * Send a one-time side effect
                     */
                    protected fun sendEffect(effect: E) {
                        viewModelScope.launch { _effect.emit(effect) }
                    }
                }
            """.trimIndent())

            // ---------- COMMON UI RESOURCE ----------

            FileGenerator.createKotlinFile(project, commonDir, "Resource.kt", """
                package $basePackage.core.common

                sealed class Resource<out T> {
                    data class Success<T>(val data: T) : Resource<T>()
                    data class Error(val message: String) : Resource<Nothing>()
                    object Loading : Resource<Nothing>()
                }
            """.trimIndent())

            // ---------- DISPATCHER PROVIDER (For Testing) ----------

            FileGenerator.createKotlinFile(project, utilDir, "DispatcherProvider.kt", """
                package $basePackage.util

                import kotlinx.coroutines.CoroutineDispatcher
                import kotlinx.coroutines.Dispatchers

                interface DispatcherProvider {
                    fun io(): CoroutineDispatcher = Dispatchers.IO
                    fun main(): CoroutineDispatcher = Dispatchers.Main
                    fun default(): CoroutineDispatcher = Dispatchers.Default
                }

                class DefaultDispatcherProvider : DispatcherProvider
            """.trimIndent())

            // ---------- EXAMPLE MVI CONTRACT ----------

            val featureDir = basePackageDir.getOrCreate("presentation/feature")
            FileGenerator.createKotlinFile(project, featureDir, "FeatureContract.kt", """
                package $basePackage.presentation.feature

                import $basePackage.core.mvi.*

                class FeatureContract {
                    
                    // The complete state of the screen
                    data class State(
                        val isLoading: Boolean = false,
                        val data: List<String> = emptyList(),
                        val error: String? = null
                    ) : UiState

                    // Things the user can do
                    sealed class Intent : UiIntent {
                        object LoadData : Intent()
                        data class OnItemClicked(val item: String) : Intent()
                    }

                    // Things that happen once (Navigation, Toasts)
                    sealed class Effect : UiEffect {
                        data class ShowToast(val message: String) : Effect()
                        object NavigateToDetails : Effect()
                    }
                }
            """.trimIndent())

            // ---------- NETWORK MODULE (Shared with others) ----------

            FileGenerator.createKotlinFile(project, diDir, "NetworkModule.kt", """
                package $basePackage.di

                import dagger.Module
                import dagger.Provides
                import dagger.hilt.InstallIn
                import dagger.hilt.components.SingletonComponent
                import retrofit2.Retrofit
                import retrofit2.converter.gson.GsonConverterFactory
                import javax.inject.Singleton

                @Module
                @InstallIn(SingletonComponent::class)
                object NetworkModule {
                    
                    @Provides
                    @Singleton
                    fun provideRetrofit(): Retrofit = Retrofit.Builder()
                        .baseUrl("https://api.example.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
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