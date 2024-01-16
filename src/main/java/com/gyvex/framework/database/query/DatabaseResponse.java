package com.gyvex.framework.database.query;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseResponse {
    private final boolean success;
    private final List<Map<String, Object>> rows;
    private final int updateCount;

    private DatabaseResponse(boolean success, List<Map<String, Object>> rows, int updateCount) {
        this.success = success;
        this.rows = rows;
        this.updateCount = updateCount;
    }

    public static DatabaseResponse success(ResultSet resultSet) throws SQLException {
        List<Map<String, Object>> rows = convertResultSetToList(resultSet);
        return new DatabaseResponse(true, rows, -1);
    }

    public static DatabaseResponse success(int updateCount) {
        return new DatabaseResponse(true, null, updateCount);
    }

    public static DatabaseResponse failure() {
        return new DatabaseResponse(false, null, -1);
    }

    public static DatabaseResponse failure(SQLException exception) {
        return new DatabaseResponse(false, null, -1);
    }

    private static List<Map<String, Object>> convertResultSetToList(ResultSet resultSet) throws SQLException {
        List<Map<String, Object>> rows = new ArrayList<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (resultSet.next()) {
            Map<String, Object> columns = new HashMap<>();
            
            for (int i = 1; i <= columnCount; i++) {
                columns.put(metaData.getColumnName(i), resultSet.getObject(i));
            }
            
            rows.add(columns);
        }

        return rows;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<Map<String, Object>> getRows() {
        return rows;
    }

    public int getUpdateCount() {
        return updateCount;
    }
}
