package com.kickstart.plugin.dependency


data class ResolvedDependency(
    val group: String,
    val artifact: String,
    val version: String
)