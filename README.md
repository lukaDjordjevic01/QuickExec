# QuickExec

A lightweight IntelliJ IDEA plugin that adds a flexible Run/Debug Configuration for executing custom executables with full argument support.

<!-- Plugin description -->
QuickExec provides a simple yet powerful way to run any executable from within your IDE. Whether you need to run system commands, custom build tools, or development utilities, QuickExec makes it easy to configure and execute them directly from IntelliJ IDEA.

Perfect for developers working with Rust, custom toolchains, or any project requiring quick access to command-line tools.
<!-- Plugin description end -->

## Features

### 🎯 Core Functionality

- **Predefined Executables**: Quick access to common tools
  - Rust Compiler (`rustc`) from PATH
  - Cargo from PATH
  - Automatic path resolution and validation

- **Custom Executables**: Run any executable on your system
  - File browser for easy selection
  - Support for executables in any location
  - Automatic validation before execution

- **Flexible Arguments**: Full command-line argument support
  - Proper parsing of quoted strings (e.g., `"hello world"`)
  - Support for complex argument patterns
  - Escape sequence handling

- **Working Directory Control**: Execute from any directory
  - Choose custom working directory
  - Defaults to project root
  - File browser for easy selection

### ✨ Advanced Features

- **Path Resolution**: Automatically displays the full path of system executables (rustc/cargo)
- **Smart Validation**: Pre-execution checks for executable existence and permissions
- **Persistent Configurations**: Save and reuse configurations across sessions

## Usage

### Creating a Configuration

1. Open **Run → Edit Configurations...**
2. Click **+** and select **Executable Runner**
3. Configure your executable:

   **Option 1: Use a predefined executable**
   - Select `RUSTC` or `CARGO` from the **Executable type** dropdown
   - The path field shows the resolved location automatically
   - Add your arguments (e.g., `--version` or `build --release`)

   **Option 2: Use a custom executable**
   - Select `CUSTOM` from the **Executable type** dropdown
   - Click **Browse...** to select your executable
   - Or type the path manually
   - Add any arguments needed

4. Set the **Working directory** (defaults to project root)
5. Click **OK** to save

### Running Your Configuration

- Click the **Run** button (▶️) or use `Shift+F10`
- Output appears in the Run tool window
- Use the **Stop** button (⏹️) to terminate running processes

## Examples

### Running Rust Compiler
```
Executable type:   RUSTC
Executable path:   /usr/bin/rustc (auto-resolved)
Arguments:         --version
Working directory: /path/to/project
```

### Building with Cargo
```
Executable type:   CARGO
Executable path:   /home/user/.cargo/bin/cargo (auto-resolved)
Arguments:         build --release
Working directory: /path/to/project
```

### Running Custom Scripts
```
Executable type:   CUSTOM
Executable path:   /usr/local/bin/my-tool
Arguments:         --input "data.txt" --verbose
Working directory: /path/to/data
```

### System Commands
```
Executable type:   CUSTOM
Executable path:   /bin/ls
Arguments:         -lah /tmp
Working directory: /tmp
```

## Installation

- Using the IDE built-in plugin system [PLUGIN NOT YET AVAILABLE ON MARKETPLACE]:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "QuickExec"</kbd> >
  <kbd>Install</kbd>

- Using JetBrains Marketplace [PLUGIN NOT YET AVAILABLE ON MARKETPLACE]:

  Go to [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID) and install it by clicking the <kbd>Install to ...</kbd> button in case your IDE is running.

  You can also download the [latest release](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID/versions) from JetBrains Marketplace and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

- Manually [ONLY OPTION CURRENTLY]:

  Download the [latest release](https://github.com/lukaDjordjevic01/QuickExec/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
[docs:plugin-description]: https://plugins.jetbrains.com/docs/intellij/plugin-user-experience.html#plugin-description-and-presentation
