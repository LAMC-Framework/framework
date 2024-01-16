package com.gyvex.framework.database.query;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import com.gyvex.framework.LAMC;
import com.gyvex.framework.database.Model;
import com.gyvex.framework.log.LogManager;

public class Builder {
    private String tableName;
    private List<String> selectColumns = new ArrayList<>();
    private List<String> whereClauses = new ArrayList<>();
    private List<Object> parameters = new ArrayList<>();
    private List<String> joinClauses = new ArrayList<>();
    private String groupByClause;
    private String havingClause;
    private String orderByClause;
    private Integer limit;
    private Integer offset;
    private Map<String, Object> insertData;
    private QueryType queryType;
    private Class<?> modelClass;

    public Builder() {
        this.parameters = new ArrayList<>();
    }
    
    public <T extends Model<T>> Builder(Class<T> modelClass) {
    	this.modelClass = modelClass;
        this.parameters = new ArrayList<>();
    }

    public Builder select(String columns) {
        selectColumns.add(columns);
        this.queryType = QueryType.SELECT;
        return this;
    }

    public Builder from(String table) {
        this.tableName = table;
        return this;
    }

    public Builder where(String column, Object value) {
        whereClauses.add(column + " = ?");
        parameters.add(value);
        return this;
    }

    public Builder where(String column, String operator, Object value) {
        whereClauses.add(column + " " + operator + " ?");
        parameters.add(value);
        return this;
    }

    public Builder orWhere(String column, String operator, Object value) {
        if (whereClauses.isEmpty()) {
            throw new IllegalStateException("Cannot add OR WHERE without a preceding WHERE clause.");
        }
        whereClauses.add(column + " " + operator + " ?");
        parameters.add(value);
        return this;
    }

    public Builder join(String table, String onClause) {
        joinClauses.add(" JOIN " + table + " ON " + onClause);
        return this;
    }

    public Builder leftJoin(String table, String onClause) {
        joinClauses.add(" LEFT JOIN " + table + " ON " + onClause);
        return this;
    }

    public Builder groupBy(String columns) {
        groupByClause = columns;
        return this;
    }

    public Builder having(String column, String operator, Object value) {
        havingClause = column + " " + operator + " ?";
        parameters.add(value);
        return this;
    }

    public Builder orderBy(String column, String direction) {
        orderByClause = column + " " + direction;
        return this;
    }

    public Builder limit(int limit) {
        this.limit = limit;
        return this;
    }

    public Builder offset(int offset) {
        this.offset = offset;
        return this;
    }

    public Builder insert(String table, Map<String, Object> data) {
        if (data.isEmpty()) {
            throw new IllegalArgumentException("No data provided for insertion");
        }
        this.tableName = table;
        this.insertData = data;
        this.queryType = QueryType.INSERT;
        return this;
    }
    
	@SuppressWarnings("unchecked")
	public <T extends Model<T>> T first() {
	    if (this.queryType != QueryType.SELECT) {
	        throw new IllegalStateException("first() method can only be used with SELECT queries.");
	    }
	    
	    DatabaseResponse response = this.execute();
	    
	    if (response == null || response.getRows().size() < 1) {
	        return null;
	    }
        
	    Object instance = null;
	    
		try {
			instance = this.modelClass.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		
        if (!(instance instanceof Model)) {
        	LogManager.warning("Class \"" + this.modelClass + "\" is not a Model");
        }

	    List<Map<String, Object>> rows = response.getRows();
	    
	    if (!rows.isEmpty()) {
	        Map<String, Object> firstRow = rows.get(0);
	        T model = (T) instance;

            for (Map.Entry<String, Object> entry : firstRow.entrySet()) {
                model.setAttribute(entry.getKey(), entry.getValue());
            }
            
			return model;
	    }

	    return null;
	}
    
    public boolean exists() {
        try (Connection conn = LAMC.getInstance().getDatabaseManager().getConnection();
             PreparedStatement stmt = conn.prepareStatement(buildExistsQuery())) {

            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
        	e.printStackTrace();
            return false;
        }
    }

    public String getQuery() {
        StringBuilder query = new StringBuilder();

        switch (queryType) {
            case SELECT:
                buildSelectQuery(query);
                break;
            case INSERT:
                buildInsertQuery(query);
                break;
        }

        return query.toString();
    }

    private void buildSelectQuery(StringBuilder query) {
        query.append("SELECT ");
        query.append(String.join(", ", selectColumns));
        query.append(" FROM ").append(tableName);

        joinClauses.forEach(query::append);

        if (!whereClauses.isEmpty()) {
            query.append(" WHERE ");
            query.append(String.join(" AND ", whereClauses));
        }

        if (groupByClause != null) {
            query.append(" GROUP BY ").append(groupByClause);
        }

        if (havingClause != null) {
            query.append(" HAVING ").append(havingClause);
        }

        if (orderByClause != null) {
            query.append(" ORDER BY ").append(orderByClause);
        }

        if (limit != null) {
            query.append(" LIMIT ").append(limit);
        }

        if (offset != null) {
            query.append(" OFFSET ").append(offset);
        }
    }
    
    private void buildInsertQuery(StringBuilder query) {
        query.append("INSERT INTO ").append(tableName).append(" (");

        StringJoiner columns = new StringJoiner(", ");
        StringJoiner values = new StringJoiner(", ");

        insertData.forEach((key, value) -> {
            columns.add(key);
            values.add("?");
            parameters.add(value);
        });

        query.append(columns).append(") VALUES (").append(values).append(")");
    }
    
    private String buildExistsQuery() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(*) FROM ").append(tableName);

        joinClauses.forEach(query::append);

        if (!whereClauses.isEmpty()) {
            query.append(" WHERE ");
            query.append(String.join(" AND ", whereClauses));
        }

        if (groupByClause != null) {
            query.append(" GROUP BY ").append(groupByClause);
        }

        if (havingClause != null) {
            query.append(" HAVING ").append(havingClause);
        }

        return query.toString();
    }


    public DatabaseResponse execute() {
        try (Connection conn = LAMC.getInstance().getDatabaseManager().getConnection();
             PreparedStatement stmt = conn.prepareStatement(this.getQuery())) {

            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }

            switch (queryType) {
                case SELECT:
                    ResultSet resultSet = stmt.executeQuery();
                    return DatabaseResponse.success(resultSet);
                case INSERT:
                case UPDATE:
                case DELETE:
                    int updateCount = stmt.executeUpdate();
                    return DatabaseResponse.success(updateCount);
                default:
                    return DatabaseResponse.failure();
            }
        } catch (SQLException e) {
            return DatabaseResponse.failure(e);
        }
    }
    
    public static Builder query() {
    	return new Builder();
    }
}
