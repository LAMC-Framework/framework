package com.gyvex.framework.database;

import com.gyvex.framework.LAMC;
import com.gyvex.framework.log.LogManager;
import com.gyvex.framework.manager.Manager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager extends Manager {
    private HikariDataSource dataSource;

    public DatabaseManager() {
        FileConfiguration dbConfig = LAMC.getInstance().config.getCustomConfig("database").getConfig();
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl("jdbc:mysql://" + dbConfig.getString("database.host") + ":" + dbConfig.getInt("database.port") + "/" + dbConfig.getString("database.database"));
        hikariConfig.setUsername(dbConfig.getString("database.username"));
        hikariConfig.setPassword(dbConfig.getString("database.password"));
        hikariConfig.addDataSourceProperty("useSSL", dbConfig.getBoolean("database.advanced.useSSL"));
        hikariConfig.setMaximumPoolSize(dbConfig.getInt("database.advanced.poolSize"));
        hikariConfig.setConnectionTimeout(dbConfig.getLong("database.advaned.timeout"));

        try {
            this.dataSource = new HikariDataSource(hikariConfig);
        } catch (Exception exception) {
        	LogManager.errorAndDisable("Could not make connection with the database", exception);
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}