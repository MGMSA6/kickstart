package com.kickstart.plugin.dependency

import com.kickstart.plugin.version.MavenVersionFetcher

object DependencyResolver {

    fun resolveLatestVersions(): Map<String, String> {
        return mapOf(
            // 🧠 Lifecycle
            "lifecycle" to MavenVersionFetcher.fetchLatestRelease(
                "androidx.lifecycle",
                "lifecycle-viewmodel-ktx"
            ),

            // 🗄️ Room
            "room" to MavenVersionFetcher.fetchLatestRelease(
                "androidx.room",
                "room-runtime"
            ),

            // 🔄 Coroutines
            "coroutines" to MavenVersionFetcher.fetchLatestRelease(
                "org.jetbrains.kotlinx",
                "kotlinx-coroutines-core"
            ),

            // 💉 Hilt
            "hilt" to MavenVersionFetcher.fetchLatestRelease(
                "com.google.dagger",
                "hilt-android"
            ),

            // 🌐 Retrofit
            "retrofit" to MavenVersionFetcher.fetchLatestRelease(
                "com.squareup.retrofit2",
                "retrofit"
            ),

            // 🌐 OkHttp
            "okhttp" to MavenVersionFetcher.fetchLatestRelease(
                "com.squareup.okhttp3",
                "okhttp"
            )
        )
            .filterValues { it != null }
            .mapValues { it.value!! }
    }
}
