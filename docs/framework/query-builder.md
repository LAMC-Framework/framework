# Query Builder

## Table of Contents

**[Overview](#overview)**

**[Using the Query Builder](#using-the-query-builder)**

**[Features](#features)**

**[Builder](#builder-query-building-methods)**

- [Select](#select)
- [From](#from)
- [Where](#where)
- [OrWhere](#orwhere)
- [Join](#join)
- [GroupBy](#groupby)
- [Having](#having)
- [OrderBy](#orderby)
- [Limit](#limit)
- [Offset](#offset)
- [Insert](#insert)
- [First](#first)
- [GetQuery](#getquery)
- [BuildExistsQuery](#buildexistsquery)
- [Query](#query)
- [Execute](#execute)
- [Exists](#exists)
   
## Overview

The LAMC Framework includes a powerful query builder, inspired by Laravel's query builder. This builder allows for complex data queries and manipulations.

## Using the Query Builder

The query builder can be accessed through model instances or statically, providing a fluent interface for building queries.

```java
MyModel.query().where("field", "value").get();
```

## Features

The Query Builder offers a range of features to simplify and streamline the process of constructing and executing database queries. These features include:

- **Fluent API:** The Query Builder provides a fluent and intuitive API for building queries, making it easier to express complex query requirements.
- **Support for complex conditions:** You can easily create queries with complex conditions and join multiple tables together. The Query Builder supports various conditional operators.
- **Integration with models:** The Query Builder seamlessly integrates with model instances, allowing you to retrieve and manipulate data from your database using your application's data models.
- **Customization:** You can customize your queries by selecting specific columns, grouping results, setting limits and offsets, and ordering data as needed.
- **Insert queries:** Create and execute INSERT queries to add new records to your database tables.

These features make the Query Builder a powerful tool for working with databases and enable you to efficiently retrieve and manipulate data in your applications.

   
## Builder - Query building methods

The `Builder` class is the core of the query builder and provides methods for constructing SQL queries.

### Select

- `select(String columns)`: Specify the columns to select in the query.

### From

- `from(String table)`: Specify the table to query from.

### Where

- `where(String column, Object value)`: Add a basic WHERE clause to the query.
- `where(String column, String operator, Object value)`: Add a WHERE clause with a custom operator.

### OrWhere

- `orWhere(String column, String operator, Object value)`: Add an OR WHERE clause. (Requires a preceding WHERE clause.)

### Join

- `join(String table, String onClause)`: Add a JOIN clause to the query.
- `leftJoin(String table, String onClause)`: Add a LEFT JOIN clause to the query.

### GroupBy

- `groupBy(String columns)`: Group the query results by specified columns.

### Having

- `having(String column, String operator, Object value)`: Add a HAVING clause to the query.

### OrderBy

- `orderBy(String column, String direction)`: Order the query results by a specific column and direction.

### Limit

- `limit(int limit)`: Limit the number of rows returned by the query.

### Offset

- `offset(int offset)`: Set an offset for the query results.

### Insert

- `insert(String table, Map<String, Object> data)`: Create an INSERT query.

### First

- `first()`: Execute a SELECT query and return the first result as a model instance.

### Exists

- `exists()`: Check if the query result exists.

### GetQuery

- `getQuery()`: Get the generated SQL query as a string.

### BuildExistsQuery

- `buildExistsQuery()`: Build a SQL query to check for existence.

## Tools - Query Execution and More

### Query

- `static Builder query()`: Create a new `Builder` instance statically.

### Execute

- `execute()`: Execute the constructed query and return a `DatabaseResponse`.