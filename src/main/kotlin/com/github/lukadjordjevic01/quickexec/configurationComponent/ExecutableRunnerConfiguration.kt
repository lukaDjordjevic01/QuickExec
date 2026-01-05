package com.github.lukadjordjevic01.quickexec.configurationComponent

import com.intellij.execution.Executor
import com.intellij.execution.configurations.*
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import org.jdom.Element

class ExecutableRunnerConfiguration(
    project: Project,
    factory: ConfigurationFactory,
    name: String
) : RunConfigurationBase<ExecutableRunnerConfiguration>(project, factory, name) {

    var executableType: ExecutableType = ExecutableType.CUSTOM
    var customExecutablePath: String = ""
    var programArguments: String = ""

    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration> =
        ExecutableRunnerConfigurationEditor()

    override fun checkConfiguration() {
    }

    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState? {
        return null
    }

    override fun writeExternal(element: Element) {
        super.writeExternal(element)
        element.setAttribute("executableType", executableType.name)
        element.setAttribute("customExecutablePath", customExecutablePath)
        element.setAttribute("programArguments", programArguments)
    }

    override fun readExternal(element: Element) {
        super.readExternal(element)
        element.getAttributeValue("executableType")?.let {
            executableType = ExecutableType.valueOf(it)
        }
        element.getAttributeValue("customExecutablePath")?.let {
            customExecutablePath = it
        }
        element.getAttributeValue("programArguments")?.let {
            programArguments = it
        }
    }

    enum class ExecutableType {
        RUSTC,
        CARGO,
        CUSTOM
    }
}