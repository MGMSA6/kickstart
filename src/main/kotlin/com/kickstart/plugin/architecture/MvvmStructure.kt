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
         │   ├─ LoginScreen.kt
         │   └─ LoginViewModel.kt
         ├─ dashboard/
         │   ├─ DashboardScreen.kt
         │   └─ DashboardViewModel.kt
         └─ theme/
             ├─ Color.kt
             ├─ Theme.kt
             └─ Type.kt

        di/
        utils/
    """.trimIndent()

    fun directories(): List<String> = listOf(
        // data
        "data/local/dao",
        "data/local/entity",
        "data/remote/api",
        "data/remote/dto",
        "data/mapper",
        "data/repository",

        // domain
        "domain/model",
        "domain/repository",
        "domain/usecase",

        // ui
        "ui/main",
        "ui/login",
        "ui/dashboard",
        "ui/theme",

        // core
        "di",
        "utils"
    )
}
