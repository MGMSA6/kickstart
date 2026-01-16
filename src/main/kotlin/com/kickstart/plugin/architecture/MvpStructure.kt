package com.kickstart.plugin.architecture

object MvpStructure {
    fun preview(): String = """
    data/
     ├─ remote/                 # API & DTOs
     ├─ repository/             # Repository Implementations
     └─ mapper/                 # Data mappers

    domain/
     ├─ model/                  # Domain Models
     └─ repository/             # Repository Interfaces

    presentation/
     └─ feature/                # Feature-based grouping
         ├─ FeatureContract.kt  # View & Presenter Interfaces
         ├─ FeaturePresenter.kt # Business Logic
         └─ FeatureActivity.kt  # UI Implementation

    di/                         # Hilt/Koin Modules

    core/
     ├─ base/                   # BasePresenter, BaseView
     └─ network/                # Network helpers

    util/                       # Constants, Result wrappers
""".trimIndent()

    fun directories(): List<String> = listOf(
        "data/remote/api", "data/remote/dto", "data/remote/interceptor", "data/repository", "data/mapper",
        "domain/model", "domain/repository",
        "presentation/feature",
        "di",
        "core/base", "core/network",
        "util"
    )
}