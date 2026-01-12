package com.kickstart.plugin.version

import java.net.URI
import javax.xml.parsers.DocumentBuilderFactory
object MavenVersionFetcher {

    private val repositories = listOf(
        "https://repo1.maven.org/maven2",
        "https://dl.google.com/dl/android/maven2"
    )

    fun fetchLatestRelease(
        groupId: String,
        artifactId: String
    ): String? {
        val path = groupId.replace(".", "/") + "/$artifactId/maven-metadata.xml"

        for (repo in repositories) {
            try {
                val url = URI("$repo/$path").toURL()
                val connection = url.openConnection().apply {
                    connectTimeout = 3000
                    readTimeout = 3000
                }

                val document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(connection.getInputStream())

                val versions = document
                    .getElementsByTagName("version")
                    .let { nodes ->
                        (0 until nodes.length)
                            .mapNotNull { nodes.item(it)?.textContent }
                            .filterNot {
                                it.contains("alpha", true) ||
                                        it.contains("beta", true) ||
                                        it.contains("rc", true)
                            }
                    }

                if (versions.isNotEmpty()) {
                    return versions.last() // latest stable
                }

            } catch (_: Exception) {
                // try next repository
            }
        }
        return null
    }
}
