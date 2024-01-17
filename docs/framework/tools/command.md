# Commands

## Table of Contents
1. [Overview](#overview)
2. [Creating a command](#creating-a-command)
3. [Command arguments](#command-arguments)
4. [Best practices](#best-practices)

## Overview
The LAMC Framework simplifies the creation and management of custom commands in Minecraft plugins. Inspired by Laravel's elegant command structure, it provides a streamlined approach to command implementation.

## Creating a command
To create a custom command, extend the `Command` class from the LAMC Framework. This class facilitates automatic registration and offers a structured template for command development.

### Example
Below is an example demonstrating the registration of the command `acommand` and the execution of the `execute` method in the `Command` class:

```java
import com.gyvex.framework.command.Command;
import org.bukkit.entity.Player;

public class MyCommand extends Command {
    @Override
    protected String name() {
        return "acommand";
    }
    
    @Override
    protected boolean execute() {
        Player player = (Player) this.sender;
        player.sendMessage("A message");
        
        return true;
    }
}
```

## Command arguments
When creating a custom command, the `Command` class provides several protected fields that are automatically populated:

- `sender`: The `CommandSender` instance that initiated the command. Often a player, but could also be the console or a command block.
- `args`: An array of `String`, representing the arguments passed with the command.
- `label`: The alias of the command used by the sender.
- `command`: The `Command` instance representing the Bukkit command.

These fields are accessible within the `execute` method and can be used to tailor the command's behavior.

## Best practices
- **Conciseness**: Keep your command logic concise and focused.
- **Descriptive Naming**: Use descriptive and intuitive names for your commands.
- **Handling Sender Types**: Check and handle different types of `sender` (e.g., Player, Console).
- **Validating Arguments**: Validate and handle `args` appropriately to ensure robust command execution.

For more comprehensive examples and detailed usage guidelines, refer to the main LAMC Framework documentation.
