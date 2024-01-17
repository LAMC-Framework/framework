# ConfigurableMessage

## Table of contents

1. [Introduction](#introduction)
2. [Configuration file requirement](#configuration-file-requirement)
3. [Method overview](#method-overview)
4. [Detailed code example](#detailed-code-example)

## Introduction

This document provides detailed information about the `ConfigurableMessage` class in the LAMC framework.

## Configuration file requirement

For `ConfigurableMessage` to function correctly, a configuration file named `messages.yml` must be included in your plugin. This file should contain all the message keys and their corresponding messages. Ensure to load this file by adding "messages" to the `configs()` list in your plugin's main class.

```java
class MyPlugin extends LAMCPlugin {
    public List<String> configs() {
        return Arrays.asList(
            "messages"
        );
    }
}
```

## Method overview

### sendMessage

- **Purpose:** Send a message from the configuration file to a `CommandSender` (usually a player).
- **Usage:** `ConfigurableMessage.sendMessage(CommandSender player, String messageKey)`

## Detailed code example

To use `ConfigurableMessage`, first ensure your plugin's main class includes "messages" in the `configs()` list to load the required configuration file. Then, use the `sendMessage` method in your plugin code as shown below:

```java
import com.gyvex.framework.message.ConfigurableMessage;

ConfigurableMessage.sendMessage(player, key);
```

In this example, `sendCustomMessage` is a method that uses `ConfigurableMessage` to send a message based on a key from the `messages.yml` configuration file.
