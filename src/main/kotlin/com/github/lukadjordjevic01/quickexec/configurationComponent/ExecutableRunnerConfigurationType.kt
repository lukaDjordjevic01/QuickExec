package com.github.lukadjordjevic01.quickexec.configurationComponent

import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationType
import com.intellij.icons.AllIcons
import javax.swing.Icon

class ExecutableRunnerConfigurationType : ConfigurationType {

    override fun getDisplayName(): String = "Executable Runner"

    override fun getConfigurationTypeDescription(): String = "Run a custom executable with arguments"

    override fun getIcon(): Icon = AllIcons.RunConfigurations.Application

    override fun getId(): String = "EXECUTABLE_RUNNER_CONFIGURATION"

    override fun getConfigurationFactories(): Array<ConfigurationFactory> {
        return arrayOf(ExecutableRunnerConfigurationFactory(this))
    }
}