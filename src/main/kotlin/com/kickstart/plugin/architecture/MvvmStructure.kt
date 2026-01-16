package com.kickstart.plugin.architecture

object MvvmStructure {
    fun preview(): String = """
    data/
     ├─ mapper/                 # DTO to Domain converters
     ├─ remote/                 # Retrofit/Ktor logic
     │   ├─ api/
     │   └─ dto/
     └─ repository/             # Repository Implementations

    domain/
     ├─ model/                  # Pure Kotlin Models
     ├─ repository/             # Repository Interfaces
     └─ usecase/                # Business Logic

    presentation/
     ├─ feature/                # Grouped by feature
     │   ├─ viewmodel/
     │   ├─ view/               # Activity/Fragment/Screen
     │   └─ state/              # UI State classes
     └─ component/              # Reusable UI elements

    di/                         # Hilt/Koin Modules

    core/                       # Base classes (BaseViewModel, etc.)
    
    util/                       # NetworkResult, Extensions, etc.
""".trimIndent()

    fun directories(): List<String> = listOf(
        "data/mapper", "data/remote/api", "data/remote/dto", "data/repository",
        "domain/model", "domain/repository", "domain/usecase",
        "presentation/feature/viewmodel", "presentation/feature/view", "presentation/feature/state", "presentation/component",
        "di", "core", "util"
    )
}