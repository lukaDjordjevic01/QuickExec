package com.github.lukadjordjevic01.quickexec.configurationComponent

import com.intellij.execution.Executor
import com.intellij.execution.configurations.*
import com.intellij.execution.process.KillableColoredProcessHandler
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.execution.util.ProgramParametersUtil
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.util.execution.ParametersListUtil
import org.jdom.Element
import java.io.File

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
        when (executableType) {
            ExecutableType.RUSTC -> {
                if (!isExecutableInPath("rustc")) {
                    throw RuntimeConfigurationError("Rustc not found in PATH. Please install Rust or select a different executable type.")
                }
            }
            ExecutableType.CARGO -> {
                if (!isExecutableInPath("cargo")) {
                    throw RuntimeConfigurationError("Cargo not found in PATH. Please install Cargo or select a different executable type.")
                }
            }
            ExecutableType.CUSTOM -> {
                if (customExecutablePath.isBlank()) {
                    throw RuntimeConfigurationError("Executable path is not specified")
                }
                val file = File(customExecutablePath)
                if (!file.exists()) {
                    throw RuntimeConfigurationError("Executable not found: $customExecutablePath")
                }
                if (!file.canExecute()) {
                    throw RuntimeConfigurationWarning("File may not be executable: $customExecutablePath")
                }
            }
        }
    }

    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState {
        return object : CommandLineState(environment) {
            override fun startProcess(): ProcessHandler {

                val executablePath = when (executableType) {
                    ExecutableType.RUSTC -> "rustc"
                    ExecutableType.CARGO -> "cargo"
                    ExecutableType.CUSTOM -> customExecutablePath
                }

                val commandLine = GeneralCommandLine().apply {
                    exePath = executablePath

                    if (programArguments.isNotBlank()) {
                        addParameters(parseArguments(programArguments))
                    }

                    withWorkDirectory(project.basePath)
                }

                return KillableColoredProcessHandler(commandLine)
            }
        }
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

    private fun isExecutableInPath(executableName: String): Boolean {
        val pathEnv = System.getenv("PATH") ?: return false
        val pathSeparator = File.pathSeparator
        val paths = pathEnv.split(pathSeparator)

        return paths.any { pathDir ->
            val executable = File(pathDir, executableName)
            executable.exists() && executable.canExecute()
        }
    }


    fun resolveExecutablePath(executableName: String): String? {
        val pathEnv = System.getenv("PATH") ?: return null
        val pathSeparator = File.pathSeparator
        val paths = pathEnv.split(pathSeparator)

        for (pathDir in paths) {
            val executable = File(pathDir, executableName)
            if (executable.exists() && executable.canExecute()) {
                return executable.absolutePath
            }
        }
        return null
    }

    
    private fun parseArguments(args: String): List<String> {
        return ParametersListUtil.parse(args)
    }
}