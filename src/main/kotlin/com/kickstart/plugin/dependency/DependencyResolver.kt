package com.kickstart.plugin.dependency

import com.kickstart.plugin.version.MavenVersionFetcher

object DependencyResolver {

    fun resolveMvvm(): List<ResolvedDependency> {
        return MvvmDependencyCatalog.dependencies.mapNotNull { dep ->
            val version =
                MavenVersionFetcher.fetchLatestRelease(dep.group, dep.name)

            version?.let {
                ResolvedDependency(
                    group = dep.group,
                    artifact = dep.name,
                    version = it
                )
            }
        }
    }
}