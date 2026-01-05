package com.github.lukadjordjevic01.quickexec.configurationComponent

import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.ui.LabeledComponent
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel

class ExecutableRunnerConfigurationEditor : SettingsEditor<ExecutableRunnerConfiguration>() {

    private val executablePathField = JBTextField()
    private val programArgumentsField = JBTextField()

    private val panel: JPanel = FormBuilder.createFormBuilder()
        .addLabeledComponent("Executable path:", executablePathField)
        .addLabeledComponent("Program arguments:", programArgumentsField)
        .addComponentFillVertically(JPanel(), 0)
        .panel

    override fun createEditor(): JComponent {
        return panel
    }

    override fun resetEditorFrom(configuration: ExecutableRunnerConfiguration) {
        executablePathField.text = configuration.customExecutablePath
        programArgumentsField.text = configuration.programArguments
    }

    override fun applyEditorTo(configuration: ExecutableRunnerConfiguration) {
        configuration.customExecutablePath = executablePathField.text
        configuration.programArguments = programArgumentsField.text
    }
}