package com.github.lukadjordjevic01.quickexec.configurationComponent

import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import java.awt.event.ItemEvent
import javax.swing.JComponent
import javax.swing.JPanel

class ExecutableRunnerConfigurationEditor : SettingsEditor<ExecutableRunnerConfiguration>() {

    private val executableTypeComboBox = ComboBox(ExecutableRunnerConfiguration.ExecutableType.entries.toTypedArray())
    private val executablePathField = TextFieldWithBrowseButton()
    private val programArgumentsField = JBTextField()

    private val panel: JPanel

    init {
        val fileChooserDescriptor = FileChooserDescriptor(
            true,
            false,
            false,
            false,
            false,
            false
        ).withTitle("Select Executable")
            .withDescription("Choose an executable file to run")

        executablePathField.addActionListener {
             FileChooser.chooseFile(
                fileChooserDescriptor,
                null,
                null
            ) { file ->
                executablePathField.text = file.path
            }
        }

        executableTypeComboBox.addItemListener { event ->
            if (event.stateChange == ItemEvent.SELECTED) {
                updatePathFieldState()
            }
        }

        panel = FormBuilder.createFormBuilder()
            .addLabeledComponent("Executable type:", executableTypeComboBox)
            .addLabeledComponent("Executable path:", executablePathField)
            .addLabeledComponent("Program arguments:", programArgumentsField)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    override fun createEditor(): JComponent {
        return panel
    }

    override fun resetEditorFrom(configuration: ExecutableRunnerConfiguration) {
        executableTypeComboBox.selectedItem = configuration.executableType
        executablePathField.text = configuration.customExecutablePath
        programArgumentsField.text = configuration.programArguments
        updatePathFieldState()
    }

    override fun applyEditorTo(configuration: ExecutableRunnerConfiguration) {
        configuration.executableType = executableTypeComboBox.selectedItem as ExecutableRunnerConfiguration.ExecutableType
        configuration.customExecutablePath = executablePathField.text
        configuration.programArguments = programArgumentsField.text
    }

    private fun updatePathFieldState() {
        val selectedType = executableTypeComboBox.selectedItem as? ExecutableRunnerConfiguration.ExecutableType
        executablePathField.isEnabled = selectedType == ExecutableRunnerConfiguration.ExecutableType.CUSTOM
    }
}