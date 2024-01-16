# Models

## Table of Contents
**[Overview](#1-overview)**

**[Defining Models](#2-defining-models)**

**[Usage](#3-usage)**
- Querying data
- Retrieving all records
- Creating records
- Accessing attributes

## 1. Overview

Models in the LAMC Framework are an essential component used for representing and managing data, offering a similar functionality to Laravel's Eloquent models. This documentation will guide you through defining and utilizing models effectively.

## 2. Defining Models

To define a model in the LAMC Framework, you should create a new Java class that extends the base `Model` class. Within your model class, define your fields and methods. Here's a basic example:

```java
public class MyModel extends Model<MyModel> {
	@Override
	protected String table() {
		return "my_table";
	}
}
```

In this example, we've created a model called `MyModel` that extends `Model<MyModel>`. The `table()` method defines the database table associated with this model.

## 3. Usage

Models in the LAMC Framework provide an intuitive and powerful way to interact with your data, offering various methods for saving, updating, and querying data.

### Querying Data

You can query data using the query builder. To create a query for a specific model, use the `query(Class<T> modelClass)` method:

```java
Builder query = Model.query(MyModel.class).select("*");
DatabaseResponse response = query.execute();
```
The above code demonstrates querying all records from the MyModel table and selecting all columns. The DatabaseResponse object contains the query result.

### Retrieving all records

To retrieve all records for a specific model, you can use the `all(Class<T> clazz)` method:

```java
List<MyModel> results = Model.all(MyModel.class);
```

This code fetches all records from the MyModel table and returns a list of MyModel instances.

### Creating records

You can create new records in the database using the `create(Class<T> modelClass, Map<String, Object> values)` method:

```java
Map<String, Object> values = new HashMap<>();
values.put("column1", "value1");
values.put("column2", "value2");

MyModel newRecord = Model.create(MyModel.class, values);
```

The create method inserts a new record into the `MyModel` table with the specified values and returns a `MyModel` instance representing the newly created record.

### Accessing attributes

You can access the attributes of a model instance using the `setAttribute(String column, Object value)` and `getAttribute(String column)` methods:

```java
MyModel model = new MyModel();
model.setAttribute("column1", "value1");
Object value = model.getAttribute("column1");
```

These methods allow you to set and retrieve attribute values on a model instance.