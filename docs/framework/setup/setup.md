
# LAMC Framework setup guide

## Table of contents

1. [Introduction](#introduction)
2. [Adding LAMC to the server](#adding-lamc-to-the-server)
3. [Migrations](#migrations)
4. [Commands](#commands)
5. [Listeners (events)](#listeners-events)

## Introduction

Welcome to the LAMC Framework, a powerful tool designed to streamline the process of developing Minecraft plugins. This guide provides comprehensive instructions for setting up the LAMC Framework on your server. Whether you're setting up the framework, creating custom migrations, or integrating commands and event listeners, this guide covers all the essentials.

## Adding LAMC to the server

1. **Download LAMC Framework:** Download the latest version from the official repository.
2. **Server Installation:** Copy the `.jar` file into your server's `plugins` directory.
3. **Restart Server:** Reboot your server to activate the framework.

## Migrations

Migrations manage database schema changes in an organized manner.

1. **Creating Migrations:** Define migration classes implementing the `Migration` interface.
2. **Migration Execution:** Load and execute migrations through the framework's command interface.

```java
// Example of a Migration class
public class CreateUsersTable implements Migration {
    @Override
    public void up() {
        // Logic to create the users table
    }

    @Override
    public void down() {
        // Logic to drop the users table
    }
}
```

## Commands

Custom commands enhance server functionality, this is why we wanted to improve coding new commands. Our setup allows the old setup to be used, but applies functionalities like:

- Autoloading commands, no more registering command by command
- Removal of onCommand, moved to abstract `execute` structure
- Default Bukkit properties are available as private properties (`this.args` by example)

1. **Define Commands:** Create command classes extending the framework's command base class.

```java
package com.yourplugin.commands;

import com.gyvex.framework.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GreetCommand extends Command {
    @Override
    public void execute() {
        // Check if the sender is a player
        if (!(this.sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return;
        }

        Player player = (Player) this.sender;

        if (this.args.length > 0) {
            // Greet the specified player
            player.sendMessage("Hello, " + this.args[0] + "!");
        } else {
            // Greet the player who issued the command
            player.sendMessage("Hello, " + player.getName() + "!");
        }
    }
}
```

2. **Command registration:** Register these commands within the plugin's initialization.

Commands initialized with the `com.gyvex.framework.command.Command` class don't have to be registered, any default `implements CommandExecutor` should be registered.

```java
public class MyPlugin extends LAMCPlugin {
    public List<Command> commands() {
        return Arrays.asList(
            new CustomCommand(),
        );
    }
}
```

## Listeners (events)

Listeners respond to various game events.

1. **Create event listeners:** Implement the necessary listeners.

```java
// Example of an event listener
public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Handle the join event
    }
}
```

2. **Listener registration:** Register your listeners in the plugin's main class.

```java
public class MyPlugin extends LAMCPlugin {
    public List<Command> listeners() {
        return Arrays.asList(
            new PlayerJoinListener(),
        );
    }
}
```