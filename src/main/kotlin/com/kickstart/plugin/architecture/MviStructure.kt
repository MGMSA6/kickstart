package com.kickstart.plugin.architecture

object MviStructure {
    fun preview(): String = """
    data/
     ├─ remote/                 # API & DTOs
     ├─ repository/             # Repository Implementations
     └─ source/                 # Local (Room) & Remote sources

    domain/
     ├─ model/                  # Domain Models
     ├─ repository/             # Repository Interfaces
     └─ usecase/                # Business Logic (Critical for MVI)

    presentation/
     └─ feature/                # Feature-based grouping
         ├─ FeatureContract.kt  # Intent, State, and SideEffect
         ├─ FeatureViewModel.kt # State Reducer & Intent Handler
         └─ FeatureScreen.kt    # UI (Compose)

    di/                         # Hilt/Koin Modules

    core/
     ├─ mvi/                    # BaseMviViewModel, MviInterfaces
     └─ common/                 # UI Resource wrappers

    util/                       # DispatcherProvider, Extensions
""".trimIndent()

    fun directories(): List<String> = listOf(
        "data/remote/api", "data/remote/dto", "data/repository", "data/source/local",
        "domain/model", "domain/repository", "domain/usecase",
        "presentation/feature",
        "di",
        "core/mvi", "core/common",
        "util"
    )
}