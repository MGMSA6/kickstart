package com.kickstart.plugin.architecture

object MvvmStructure {

    fun preview(): String = """
    data/
     ├─ mapper/
     ├─ remote/
     │   ├─ api/
     │   ├─ dto/
     │   ├─ interceptor/
     │   └─ NetworkClient.kt
     └─ repository/
         └─ *RepositoryImpl.kt

    domain/
     ├─ model/
     └─ repository/

    presentation/
     ├─ navigation/
     ├─ screens/
     ├─ state/
     └─ viewmodel/

    di/
     └─ NetworkModule.kt

    util/
     ├─ ApiConstants.kt
     ├─ NetworkResult.kt
     ├─ ErrorHandler.kt
     ├─ ConnectivityChecker.kt
     └─ HeaderProvider.kt
""".trimIndent()


    fun directories(): List<String> = listOf(

        // data
        "data/mapper",
        "data/remote/api",
        "data/remote/dto",
        "data/remote/interceptor",
        "data/repository",

        // domain
        "domain/model",
        "domain/repository",

        // presentation (from screenshot)
        "presentation/navigation",
        "presentation/screens",
        "presentation/state",
        "presentation/viewmodel",

        // dependency injection
        "di",

        // utilities / core
        "util"
    )

}
