package com.kickstart.plugin.dependency

object MvvmDependencyCatalog {

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
            alias = "kotlinx-coroutines-android",
            group = "org.jetbrains.kotlinx",
            name = "kotlinx-coroutines-android"
        )
    )
}