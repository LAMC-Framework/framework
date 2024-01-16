package com.gyvex.framework.database.query;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.gyvex.framework.LAMC;
import com.gyvex.framework.log.LogManager;

public class Table {
    private final String tableName;
    private final List<Column> columns = new ArrayList<>();
    private final List<String> primaryKeyColumns = new ArrayList<>();

    public Table(String tableName) {
        this.tableName = tableName;
    }

    public Table columns(Consumer<Table> columnDefinitions) {
        columnDefinitions.accept(this);
        
		try {
	        Statement statement = LAMC.getInstance().getDatabaseManager().getConnection().createStatement();
	        statement.execute(this.getQuery());
		} catch (SQLException e) {
			LogManager.errorAndDisable("Could not create table " + this.tableName, e);
		}
        
        return this;
    }

    public Table primary(String... columnNames) {
        for (String columnName : columnNames) {
            primaryKeyColumns.add(columnName);
        }
        
        return this;
    }

    public String getQuery() {
        StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        query.append(tableName).append(" (");

        for (Column column : columns) {
            query.append(column.buildDefinition()).append(", ");
        }

        if (!primaryKeyColumns.isEmpty()) {
            query.append("PRIMARY KEY (");
            
            for (String columnName : primaryKeyColumns) {
                query.append(columnName).append(", ");
            }
            
            query.setLength(query.length() - 2);
            query.append("), ");
        }

        query.setLength(query.length() - 2);
        query.append(");");

        return query.toString();
    }

    public Column id() {
        Column column = new Column("id", "INT");
        column.autoIncrement();
        columns.add(column);
        
        this.primary("id");
        
        return column;
    }

    public Column string(String columnName, int length) {
        Column column = new Column(columnName, "VARCHAR(" + length + ")");
        columns.add(column);
        
        return column;
    }
    
    public Column number(String columnName, int length) {
        Column column = new Column(columnName, "INT(" + length + ")");
        columns.add(column);
        
        return column;
    }

    public Column required() {
        if (!columns.isEmpty()) {
            columns.get(columns.size() - 1).required();
        }
        
        return columns.get(columns.size() - 1);
    }

    public Column nullable() {
        if (!columns.isEmpty()) {
            columns.get(columns.size() - 1).nullable();
        }
        
        return columns.get(columns.size() - 1);
    }

    public Column unique() {
        if (!columns.isEmpty()) {
            columns.get(columns.size() - 1).unique();
        }
        
        return columns.get(columns.size() - 1);
    }

    public Table timestamps() {
        string("created_at", 255).required();
        string("updated_at", 255).required();
        
        return this;
    }

    public class Column {
        private final String columnName;
        private final String dataType;
        private final List<String> modifiers = new ArrayList<>();

        public Column(String columnName, String dataType) {
            this.columnName = columnName;
            this.dataType = dataType;
        }

        public Column autoIncrement() {
            modifiers.add("AUTO_INCREMENT");
            
            return this;
        }

        public Column required() {
            modifiers.add("NOT NULL");
            
            return this;
        }

        public Column nullable() {
            modifiers.add("NULL");
            
            return this;
        }

        public Column unique() {
            modifiers.add("UNIQUE");
            
            return this;
        }

        private String buildDefinition() {
            StringBuilder definition = new StringBuilder(columnName).append(" ").append(dataType);
            
            for (String modifier : modifiers) {
                definition.append(" ").append(modifier);
            }
            
            return definition.toString();
        }
    }
}