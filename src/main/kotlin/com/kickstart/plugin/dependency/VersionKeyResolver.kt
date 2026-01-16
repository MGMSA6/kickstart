package com.kickstart.plugin.dependency

object VersionKeyResolver {

    fun versionKeyFor(dep: CatalogDependency): String =
        when (dep.group) {
            "androidx.lifecycle" -> "lifecycle"
            "androidx.room" -> "room"
            "org.jetbrains.kotlinx" -> "coroutines"
            "com.google.dagger", "androidx.hilt" -> "hilt"
            "com.squareup.retrofit2" -> "retrofit"
            "com.squareup.okhttp3" -> "okhttp"
            else -> dep.alias.replace("-", "_")
        }
}
