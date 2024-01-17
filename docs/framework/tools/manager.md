# Managers

## Introduction

In software engineering, particularly in the development of large and complex systems, the use of manager-like structures is pivotal for maintaining organization, scalability, and efficiency. Managers in frameworks like LAMC play a crucial role in abstracting complex functionalities into manageable, reusable components. They allow for a separation of concerns, where different aspects of the application are handled by specialized managers. This not only simplifies the codebase but also enhances maintainability and facilitates easier debugging and testing.

Utilizing a manager-like structure offers several benefits:

- **Modularity:** Each manager handles a specific aspect of the framework, promoting a modular design.
- **Ease of Maintenance:** Changes in one part of the system can be made with minimal impact on others.
- **Reusability:** Common functionalities can be reused across different parts of the application.
- **Abstraction:** Complex logic is hidden behind a simple interface, making the framework user-friendly.

In the LAMC framework, managers are integral components that provide essential services like configuration management, database interactions, migration handling, and logging. They are designed to be easily accessible and straightforward to use, enhancing the developer experience.

## Table of contents

- [ConfigManager](#configmanager)
- [DatabaseManager](#databasemanager)
- [MigrationManager](#migrationmanager)
- [LogManager](#logmanager)
- [Manager](#manager)

## ConfigManager

**Package:** `com.gyvex.framework.config`

### Description

Keep track of config files, load, and reloading. Accessible by `LAMC.getInstance().getConfigManager()` or `plugin.getKernel().getManager('config')`.

### Example Usage

```java
ConfigManager configManager = LAMC.getInstance().getConfigManager();
```

## DatabaseManager

**Package:** `com.gyvex.framework.database`

### Description

Open database connection and hold it for usage.

### Example Usage

```java
DatabaseManager dbManager = LAMC.getInstance().getDatabaseManager();
Connection connection = dbManager.getConnection();
```

## MigrationManager

**Package:** `com.gyvex.framework.database.migration`

### Description
Responsible for running, loading, and holding the migration files.

### Example Usage
```java
MigrationManager migrationManager = LAMC.getInstance().getMigrationManager();
```

## LogManager

**Package:** `com.gyvex.framework.log`

### Description

Provides logging functionality throughout the framework.

### Example Usage

```java
LogManager logManager = LAMC.getInstance().getLogManager();
logManager.info("Example log message");
```

## Manager

**Package:** `com.gyvex.framework.manager`

### Description

General structure for Managers in the LAMC framework.

### Example Usage

```java
// Generic manager example
ConfigManager manager = plugin.getKernel().getManager(ConfigManager.class);
FileConfiguration config = manager.getCustomConfig("config").getConfig();
```