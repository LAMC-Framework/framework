package com.gyvex.framework;

import com.gyvex.framework.config.ConfigManager;
import com.gyvex.framework.database.DatabaseManager;
import com.gyvex.framework.log.LogManager;

public class LAMC {
	private static LAMC instance;
    private final LAMCPlugin plugin;
    private final Kernel kernel;
    
    public LogManager log;
    public ConfigManager config;
    public DatabaseManager database;

    public LAMC(LAMCPlugin plugin) {
        instance = this;
        this.plugin = plugin;
        this.kernel = new Kernel();

        this.log = kernel.getManager(LogManager.class);
        this.config = kernel.getManager(ConfigManager.class);
        this.database = kernel.getManager(DatabaseManager.class);
    }

    public static LAMC getInstance() {
        return instance;
    }

    public LAMCPlugin getPlugin() {
        return plugin;
    }

    public LogManager getLogManager() {
        return this.log;
    }
    
    public void setLogManager(LogManager log) {
    	this.log = log;
    }

    public ConfigManager getConfigManager() {
        return this.config;
    }
    
    public void setConfigManager(ConfigManager config) {
    	this.config = config;
    }

    public DatabaseManager getDatabaseManager() {
        return this.database;
    }
    
    public void setDatabaseManager(DatabaseManager database) { 
    	this.database = database;
    }
}
