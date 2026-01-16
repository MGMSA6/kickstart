package com.kickstart.plugin.ui

import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogWrapper
import com.kickstart.plugin.architecture.ArchitectureStructureProvider
import com.kickstart.plugin.architecture.ArchitectureType
import java.awt.Dimension
import javax.swing.*

class KickstartWizardDialog : DialogWrapper(true) {

    private lateinit var mainPanel: JPanel
    private lateinit var architectureCombo: JComboBox<ArchitectureType>
    private lateinit var previewArea: JTextArea

    init {
        title = "Kickstart Project Setup"
        init()
    }

    override fun createCenterPanel(): JComponent {
        mainPanel = JPanel()
        mainPanel.layout = BoxLayout(mainPanel, BoxLayout.Y_AXIS)

        mainPanel.add(JLabel("Select Architecture"))

        architectureCombo = ComboBox(ArchitectureType.entries.toTypedArray())
        architectureCombo.maximumSize = Dimension(Int.MAX_VALUE, 30)

        mainPanel.add(Box.createVerticalStrut(8))
        mainPanel.add(architectureCombo)

        mainPanel.add(Box.createVerticalStrut(12))
        mainPanel.add(JLabel("Folder Structure Preview"))

        previewArea = JTextArea(14, 50)
        previewArea.isEditable = false
        previewArea.font = previewArea.font.deriveFont(12f)

        updatePreview()

        architectureCombo.addActionListener {
            updatePreview()
        }

        mainPanel.add(Box.createVerticalStrut(6))
        mainPanel.add(JScrollPane(previewArea))

        return mainPanel
    }

    private fun updatePreview() {
        val selected = architectureCombo.selectedItem as ArchitectureType
        previewArea.text = ArchitectureStructureProvider.getStructure(selected)
    }

    fun getSelectedArchitecture(): ArchitectureType =
        architectureCombo.selectedItem as ArchitectureType
}
