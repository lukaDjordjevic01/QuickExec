package com.github.lukadjordjevic01.quickexec

import com.github.lukadjordjevic01.quickexec.configurationComponent.ExecutableRunnerConfiguration
import com.github.lukadjordjevic01.quickexec.configurationComponent.ExecutableRunnerConfigurationFactory
import com.github.lukadjordjevic01.quickexec.configurationComponent.ExecutableRunnerConfigurationType
import com.intellij.execution.configurations.RuntimeConfigurationError
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class MyPluginTest : BasePlatformTestCase() {

    fun testConfigurationTypeId() {
        val configurationType = ExecutableRunnerConfigurationType()
        assertEquals("EXECUTABLE_RUNNER_CONFIGURATION", configurationType.id)
    }

    fun testConfigurationTypeDisplayName() {
        val configurationType = ExecutableRunnerConfigurationType()
        assertEquals("Executable Runner", configurationType.displayName)
    }

    fun testConfigurationFactoryCreatesCorrectType() {
        val configurationType = ExecutableRunnerConfigurationType()
        val factory = ExecutableRunnerConfigurationFactory(configurationType)
        val configuration = factory.createTemplateConfiguration(project)

        assertTrue(configuration is ExecutableRunnerConfiguration)
    }

    fun testExecutableTypeHasThreeOptions() {
        val types = ExecutableRunnerConfiguration.ExecutableType.entries
        assertEquals(3, types.size)
    }

    fun testDefaultConfigurationIsCustomType() {
        val factory = ExecutableRunnerConfigurationFactory(ExecutableRunnerConfigurationType())
        val configuration = ExecutableRunnerConfiguration(project, factory, "Test")

        assertEquals(ExecutableRunnerConfiguration.ExecutableType.CUSTOM, configuration.executableType)
    }

    fun testEmptyCustomExecutablePathThrowsError() {
        val factory = ExecutableRunnerConfigurationFactory(ExecutableRunnerConfigurationType())
        val configuration = ExecutableRunnerConfiguration(project, factory, "Test")

        configuration.executableType = ExecutableRunnerConfiguration.ExecutableType.CUSTOM
        configuration.customExecutablePath = ""

        assertThrows(RuntimeConfigurationError::class.java) {
            configuration.checkConfiguration()
        }
    }
}