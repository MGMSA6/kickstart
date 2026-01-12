package com.kickstart.plugin.dependency

import com.kickstart.plugin.version.MavenVersionFetcher

object DependencyResolver {

    fun resolveLatestVersions(): Map<String, String> {
        return mapOf(
            "lifecycle" to MavenVersionFetcher.fetchLatestRelease(
                "androidx.lifecycle",
                "lifecycle-viewmodel-ktx"
            ),
            "room" to MavenVersionFetcher.fetchLatestRelease(
                "androidx.room",
                "room-runtime"
            ),
            "coroutines" to MavenVersionFetcher.fetchLatestRelease(
                "org.jetbrains.kotlinx",
                "kotlinx-coroutines-core"
            ),
            "hilt" to MavenVersionFetcher.fetchLatestRelease(
                "com.google.dagger",
                "hilt-android"
            )
        ).filterValues { it != null }
            .mapValues { it.value!! }
    }
}
