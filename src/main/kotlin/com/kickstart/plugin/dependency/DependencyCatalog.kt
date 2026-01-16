package com.kickstart.plugin.dependency

object DependencyCatalog {
    val dependencies = listOf(
        CatalogDependency(
            alias = "androidx-lifecycle-viewmodel-ktx",
            group = "androidx.lifecycle",
            name = "lifecycle-viewmodel-ktx"
        ),
        CatalogDependency(
            alias = "androidx-lifecycle-livedata-ktx",
            group = "androidx.lifecycle",
            name = "lifecycle-livedata-ktx"
        ),
        CatalogDependency(
            alias = "androidx-lifecycle-runtime-ktx",
            group = "androidx.lifecycle",
            name = "lifecycle-runtime-ktx"
        ),
        CatalogDependency(
            alias = "androidx-lifecycle-viewmodel-savedstate",
            group = "androidx.lifecycle",
            name = "lifecycle-viewmodel-savedstate"
        ),

        // 🗄️ Room (Database)
        CatalogDependency(
            alias = "androidx-room-runtime",
            group = "androidx.room",
            name = "room-runtime"
        ),
        CatalogDependency(
            alias = "androidx-room-compiler",
            group = "androidx.room",
            name = "room-compiler",
            scope = DependencyScope.KSP
        ),

        // 🔄 Kotlin Coroutines
        CatalogDependency(
            alias = "kotlinx-coroutines-core",
            group = "org.jetbrains.kotlinx",
            name = "kotlinx-coroutines-core"
        ),
        CatalogDependency(
            alias = "kotlinx-coroutines-android",
            group = "org.jetbrains.kotlinx",
            name = "kotlinx-coroutines-android"
        ),
        CatalogDependency(
            alias = "kotlinx-coroutines-test",
            group = "org.jetbrains.kotlinx",
            name = "kotlinx-coroutines-test",
            scope = DependencyScope.TEST
        ),

        // 🌐 Networking (Retrofit + OkHttp)
        CatalogDependency(
            alias = "retrofit",
            group = "com.squareup.retrofit2",
            name = "retrofit"
        ),
        CatalogDependency(
            alias = "retrofit-converter-gson",
            group = "com.squareup.retrofit2",
            name = "converter-gson"
        ),
        CatalogDependency(
            alias = "okhttp",
            group = "com.squareup.okhttp3",
            name = "okhttp"
        ),
        CatalogDependency(
            alias = "okhttp-logging-interceptor",
            group = "com.squareup.okhttp3",
            name = "logging-interceptor"
        ),

        // 💉 Hilt (Dependency Injection)
        CatalogDependency(
            alias = "hilt-android",
            group = "com.google.dagger",
            name = "hilt-android"
        ),
        CatalogDependency(
            alias = "hilt-compiler",
            group = "com.google.dagger",
            name = "hilt-compiler",
            scope = DependencyScope.KSP
        ),
        CatalogDependency(
            alias = "hilt-lifecycle-viewmodel",
            group = "androidx.hilt",
            name = "hilt-lifecycle-viewmodel",
            scope = DependencyScope.KSP
        )
    )
}
