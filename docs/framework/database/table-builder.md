# Table Builder

## Table of contents

1. [Introduction](#introduction)
2. [Using the table builder](#using-the-table-builder)
3. [Defining columns](#defining-columns)
4. [Example](#example)

## Introduction

The Table Builder in the LAMC Framework is a robust utility for defining and modifying database tables programmatically. It offers a fluent API that simplifies the process of constructing database schemas.

## Using the Table Builder

The Table Builder is utilized to declaratively define the structure of database tables, including columns, primary keys, and other attributes.

### Example Usage

```java
new Table("table_name").columns(table -> { /* column definitions */ });
```

## Defining columns

The `columns` method in the Table Builder accepts a lambda expression, allowing you to define the table's columns in a concise and readable manner.

### Column Definitions

- **ID Column:**

```java
table.id(); // Adds an 'id' column (primary key, auto-increment)
```
  
- **String Column:**

```java
table.string("column_name", 255); // Adds a string column with specified length
```
  
- **Number Column:**

```java
table.number("column_name", 10); // Adds a numeric column with specified length
```

## Example

An example of using the Table Builder to create a 'migrations' table:

```java
public class CreateMigrationsTable implements Migration {

    @Override
    public void up() {
        new Table("migrations").columns(table -> {
            table.id();
            table.string("migration_name", 255);
            table.number("executed", 1);
        });
    }

    @Override
    public void down() {
        // Logic to drop the 'migrations' table (if applicable)
    }

    @Override
    public String getMigrationName() {
        return "202401151810_create_migrations_table";
    }
}
```
