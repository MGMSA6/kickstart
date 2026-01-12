package com.kickstart.plugin.dependency

object MvvmDependencyCatalog {
    val dependencies = listOf(

        // Lifecycle / MVVM
        CatalogDependency("androidx-lifecycle-viewmodel-ktx", "androidx.lifecycle", "lifecycle-viewmodel-ktx"),
        CatalogDependency("androidx-lifecycle-livedata-ktx", "androidx.lifecycle", "lifecycle-livedata-ktx"),
        CatalogDependency("androidx-lifecycle-runtime-ktx", "androidx.lifecycle", "lifecycle-runtime-ktx"),
        CatalogDependency("androidx-lifecycle-viewmodel-savedstate", "androidx.lifecycle", "lifecycle-viewmodel-savedstate"),

        // Room
        CatalogDependency("androidx-room-runtime", "androidx.room", "room-runtime"),
        CatalogDependency(
            "androidx-room-compiler",
            "androidx.room",
            "room-compiler",
            scope = DependencyScope.KSP
        ),

        // Coroutines
        CatalogDependency("kotlinx-coroutines-core", "org.jetbrains.kotlinx", "kotlinx-coroutines-core"),
        CatalogDependency("kotlinx-coroutines-android", "org.jetbrains.kotlinx", "kotlinx-coroutines-android"),

        // Hilt
        CatalogDependency("hilt-android", "com.google.dagger", "hilt-android"),
        CatalogDependency(
            "hilt-compiler",
            "com.google.dagger",
            "hilt-compiler",
            scope = DependencyScope.KSP
        ),
        CatalogDependency(
            "hilt-lifecycle-viewmodel",
            "androidx.hilt",
            "hilt-lifecycle-viewmodel",
            scope = DependencyScope.KSP
        )
    )
}