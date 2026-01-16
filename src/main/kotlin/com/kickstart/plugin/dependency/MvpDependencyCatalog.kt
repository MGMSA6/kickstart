package com.kickstart.plugin.dependency

object MvpDependencyCatalog {

    val dependencies = listOf(
        CatalogDependency(
            alias = "kotlinx-coroutines-android",
            group = "org.jetbrains.kotlinx",
            name = "kotlinx-coroutines-android"
        ),
        CatalogDependency(
            alias = "retrofit",
            group = "com.squareup.retrofit2",
            name = "retrofit"
        ),
        CatalogDependency(
            alias = "converter-gson",
            group = "com.squareup.retrofit2",
            name = "converter-gson"
        )
    )
}
