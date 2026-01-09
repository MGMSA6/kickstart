package com.kickstart.plugin.architecture

object MvvmStructure {

    fun preview(): String = """
        data/
         ├─ local/
         │   ├─ dao/
         │   └─ entity/
         ├─ remote/
         │   ├─ api/
         │   └─ dto/
         ├─ mapper/
         └─ repository/

        domain/
         ├─ model/
         ├─ repository/
         └─ usecase/

        ui/
         ├─ main/
         │   ├─ MainActivity.kt
         │   └─ MainViewModel.kt
         ├─ login/
         │   ├─ LoginActivity.kt
         │   ├─ LoginFragment.kt
         │   └─ LoginViewModel.kt
         └─ dashboard/
             ├─ DashboardFragment.kt
             └─ DashboardViewModel.kt

        di/
        utils/

        App.kt
    """.trimIndent()

    fun directories(): List<String> = listOf(
        "data/local/dao",
        "data/local/entity",
        "data/remote/api",
        "data/remote/dto",
        "data/mapper",
        "data/repository",

        "domain/model",
        "domain/repository",
        "domain/usecase",

        "ui/main",
        "ui/login",
        "ui/dashboard",

        "di",
        "utils"
    )
}