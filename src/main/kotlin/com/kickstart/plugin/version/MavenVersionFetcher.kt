package com.kickstart.plugin.version

import java.net.URI
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory

object MavenVersionFetcher {

    fun fetchLatestRelease(
        groupId: String,
        artifactId: String
    ): String? {
        return try {
            val path = groupId.replace(".", "/")
            val url =
                "https://repo1.maven.org/maven2/$path/$artifactId/maven-metadata.xml"

            val connection = URI(url).toURL().openConnection()
            connection.connectTimeout = 3000
            connection.readTimeout = 3000

            val document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(connection.getInputStream())

            document
                .getElementsByTagName("release")
                .item(0)
                ?.textContent
        } catch (e: Exception) {
            null
        }
    }
}