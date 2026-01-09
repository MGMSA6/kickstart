package com.kickstart.plugin.ui

import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogWrapper
import javax.swing.*
import javax.swing.JScrollPane
import javax.swing.JTextArea

class KickstartWizardDialog : DialogWrapper(true) {

    private lateinit var mainPanel: JPanel
    private lateinit var architectureCombo: JComboBox<String>
    private lateinit var previewArea: JTextArea


    init {
        title = "Kickstart Project Setup"
        init()
    }

    override fun createCenterPanel(): JComponent {
        mainPanel = JPanel()
        mainPanel.layout = BoxLayout(mainPanel, BoxLayout.Y_AXIS)

        mainPanel.add(JLabel("Select Architecture"))

        architectureCombo = ComboBox(
            arrayOf("Clean Architecture", "MVVM", "MVP", "MVI", "MVC", "Redux", "VIPER")
        )

        mainPanel.add(Box.createVerticalStrut(8))
        mainPanel.add(architectureCombo)

        mainPanel.add(Box.createVerticalStrut(12))
        mainPanel.add(JLabel("Folder Structure Preview"))

        previewArea = JTextArea(10, 40)
        previewArea.isEditable = false

        updatePreview()

        architectureCombo.addActionListener {
            updatePreview()
        }

        mainPanel.add(Box.createVerticalStrut(6))
        mainPanel.add(JScrollPane(previewArea))

        return mainPanel
    }

    private fun updatePreview() {
        val selected = architectureCombo.selectedItem as String
        previewArea.text =
            com.kickstart.plugin.architecture.ArchitectureStructureProvider
                .getStructure(selected)
    }


    fun getSelectedArchitecture(): String {
        return architectureCombo.selectedItem as String
    }
}
