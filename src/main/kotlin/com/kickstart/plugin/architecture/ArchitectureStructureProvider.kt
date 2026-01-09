package com.kickstart.plugin.architecture

object ArchitectureStructureProvider {

    fun getStructure(architecture: String): String {
        return when (architecture) {
            "MVVM" -> MvvmStructure.preview()
            "Clean Architecture" -> clean()
            "MVP" -> mvp()
            else -> "No structure defined yet."
        }
    }

    private fun mvvm(): String = """
        presentation/
         ├─ ui/
         ├─ viewmodel/
        domain/
         ├─ model/
         ├─ usecase/
        data/
         ├─ repository/
         ├─ datasource/
    """.trimIndent()

    private fun clean(): String = """
        app/
        ├─ presentation/
        ├─ domain/
        ├─ data/
        core/
        di/
    """.trimIndent()

    private fun mvp(): String = """
        view/
        presenter/
        model/
    """.trimIndent()
}