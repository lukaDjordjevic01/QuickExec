package com.github.lukadjordjevic01.quickexec.configurationComponent

import com.intellij.execution.configuration.EnvironmentVariablesComponent
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
    private val workingDirectoryField = TextFieldWithBrowseButton()
    private val environmentVariablesComponent = EnvironmentVariablesComponent()

    private val panel: JPanel
    private var currentConfiguration: ExecutableRunnerConfiguration? = null

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

        val directoryChooserDescriptor = FileChooserDescriptor(
            false,
            true,
            false,
            false,
            false,
            false
        ).withTitle("Select Working Directory")
            .withDescription("Choose the working directory for the executable")

        workingDirectoryField.addActionListener {
            FileChooser.chooseFile(
                directoryChooserDescriptor,
                null,
                null
            ) { file ->
                workingDirectoryField.text = file.path
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
            .addLabeledComponent("Working directory:", workingDirectoryField)
            .addComponent(environmentVariablesComponent)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    override fun createEditor(): JComponent {
        return panel
    }

    override fun resetEditorFrom(configuration: ExecutableRunnerConfiguration) {
        currentConfiguration = configuration
        executableTypeComboBox.selectedItem = configuration.executableType

        executablePathField.text = when (configuration.executableType) {
            ExecutableRunnerConfiguration.ExecutableType.RUSTC ->
                configuration.resolveExecutablePath("rustc") ?: ""
            ExecutableRunnerConfiguration.ExecutableType.CARGO ->
                configuration.resolveExecutablePath("cargo") ?: ""
            ExecutableRunnerConfiguration.ExecutableType.CUSTOM ->
                configuration.customExecutablePath
        }

        programArgumentsField.text = configuration.programArguments
        workingDirectoryField.text = configuration.workingDirectoryPath
        environmentVariablesComponent.envs = configuration.environmentVariables
        updatePathFieldState()
    }

    override fun applyEditorTo(configuration: ExecutableRunnerConfiguration) {
        configuration.executableType = executableTypeComboBox.selectedItem as ExecutableRunnerConfiguration.ExecutableType

        if (configuration.executableType == ExecutableRunnerConfiguration.ExecutableType.CUSTOM) {
            configuration.customExecutablePath = executablePathField.text
        }

        configuration.programArguments = programArgumentsField.text
        configuration.workingDirectoryPath = workingDirectoryField.text
        configuration.environmentVariables.clear()
        configuration.environmentVariables.putAll(environmentVariablesComponent.envs)
    }

    private fun updatePathFieldState() {
        val selectedType = executableTypeComboBox.selectedItem as? ExecutableRunnerConfiguration.ExecutableType
        val config = currentConfiguration

        if (config != null) {
            executablePathField.text = when (selectedType) {
                ExecutableRunnerConfiguration.ExecutableType.RUSTC ->
                    config.resolveExecutablePath("rustc") ?: ""
                ExecutableRunnerConfiguration.ExecutableType.CARGO ->
                    config.resolveExecutablePath("cargo") ?: ""
                ExecutableRunnerConfiguration.ExecutableType.CUSTOM ->
                    config.customExecutablePath
                else -> ""
            }
        }

        executablePathField.isEnabled = selectedType == ExecutableRunnerConfiguration.ExecutableType.CUSTOM
    }
}