package com.kickstart.plugin.architecture

object ArchitectureStructureProvider {

    fun getStructure(type: ArchitectureType): String = when (type) {
        ArchitectureType.MVP -> MvpStructure.preview()
        ArchitectureType.MVVM -> MvvmStructure.preview()
        ArchitectureType.MVI -> MviStructure.preview()
    }

    fun getDirectories(type: ArchitectureType): List<String> = when (type) {
        ArchitectureType.MVP -> MvpStructure.directories()
        ArchitectureType.MVVM -> MvvmStructure.directories()
        ArchitectureType.MVI -> MviStructure.directories()
    }

}