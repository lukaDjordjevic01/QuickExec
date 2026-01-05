package com.github.lukadjordjevic01.quickexec.configurationComponent

import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationType
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.openapi.project.Project

class ExecutableRunnerConfigurationFactory(type: ConfigurationType) : ConfigurationFactory(type) {

    override fun createTemplateConfiguration(project: Project): RunConfiguration {
        return ExecutableRunnerConfiguration(project, this, "Executable Runner")
    }
}