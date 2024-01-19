package com.gyvex.framework.database;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.gyvex.framework.database.query.Builder;
import com.gyvex.framework.database.query.DatabaseResponse;
import com.gyvex.framework.log.LogManager;

public abstract class Model<T> {
    private final Map<String, Object> attributes = new HashMap<>();
    
    protected abstract String table();

    protected void mapColumns(Map<String, Object> row) {
        row.forEach(this::setAttribute);
    }

    public static <T extends Model<T>> Builder query(Class<T> modelClass) {
        T instance = null;

        try {
            instance = modelClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            
            return null;
        }

    	String table = Model.getTable(modelClass, instance);
        return new Builder(modelClass).from(table);
    }

    public void setAttribute(String column, Object value) {
        attributes.put(column, value);
    }
    
    public Object getAttribute(String column) {
        return attributes.get(column);
    }

    public static <T extends Model<T>> T find(int id, Function<Integer, T> finder) {
        return finder.apply(id);
    }

    public static <T extends Model<T>> List<T> all(Class<T> clazz) {
        List<T> results = new ArrayList<>();

        try {
            Builder query = query(clazz).select("*");
            DatabaseResponse response = query.execute();
            
            LogManager.info("[TheGunProject] Running query " + query.getQuery());
            
            if (response.isSuccess() && response.getRows() != null) {
                List<Map<String, Object>> rows = response.getRows();

                LogManager.info("Found database result... " + rows.toString());

                for (Map<String, Object> row : rows) {
                    T item = clazz.getDeclaredConstructor().newInstance();
                    item.mapColumns(row);
                    results.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return results;
    }

    public static <T extends Model<T>> T create(Class<T> modelClass, Map<String, Object> values) {
        T instance = null;

        try {
            instance = modelClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            
            return null;
        }

    	String table = Model.getTable(modelClass, instance);

        try {
            DatabaseResponse response = new Builder().insert(table, values).execute();

            if (!response.isSuccess()) {
                throw new SQLException("Failed to create new record in table: " + table);
            }

            values.forEach(instance::setAttribute);

            return instance;
        } catch (SQLException e) {
            e.printStackTrace();
            
            return null;
        }
    }
    
	public static <T extends Model<T>> String getTable(Class<T> modelClass, T instance) {
        if (!(instance instanceof Model)) {
        	LogManager.info(modelClass.toString() + " is not a Model");
        	
        	return null;
        }

        return instance.table();
    }
}