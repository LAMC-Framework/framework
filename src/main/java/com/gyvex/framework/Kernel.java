package com.gyvex.framework;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import com.gyvex.framework.command.Command;
import com.gyvex.framework.config.ConfigManager;
import com.gyvex.framework.database.DatabaseManager;
import com.gyvex.framework.database.migration.Migration;
import com.gyvex.framework.database.migration.MigrationManager;
import com.gyvex.framework.log.LogManager;
import com.gyvex.framework.manager.Manager;

public class Kernel {
	private final Map<Class<? extends Manager>, Manager> managers = new HashMap<>();
    
    public void load() {
        this.loadManagers();
        this.registerCommands();
        this.registerListeners();
    }
    
    private void loadManagers() {
    	LogManager logManager = new LogManager();
    	logManager.logInitialization();
    	LAMC.getInstance().setLogManager(logManager);
    	this.addManager(logManager);
    	
    	ConfigManager configManager = new ConfigManager(LAMC.getInstance().getPlugin().configs());
    	configManager.logInitialization();
    	LAMC.getInstance().setConfigManager(configManager);
        this.addManager(configManager);
        
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.logInitialization();
        LAMC.getInstance().setDatabaseManager(databaseManager);
        this.addManager(databaseManager);
        
        MigrationManager migrationManager = new MigrationManager();
        List<Migration> migrations = LAMC.getInstance().getPlugin().migrations();
        migrationManager.loadMigrations(migrations);
        migrationManager.logInitialization();
        migrationManager.migrate();
        this.addManager(migrationManager);
    }

    private void registerCommands() {
        Collection<Command> commands = LAMC.getInstance().getPlugin().commands();
        
        if (commands != null) {
            for (Command command : commands) {
            	LAMC.getInstance().getPlugin().getCommand(command.name()).setExecutor(command);
            }
        }
    }

    private void registerListeners() {
        Collection<Listener> listeners = LAMC.getInstance().getPlugin().listeners();
        
        if (listeners != null) {
            for (Listener listener : listeners) {
                Bukkit.getServer().getPluginManager().registerEvents(listener, LAMC.getInstance().getPlugin());
            }
        }
    }

    private <T extends Manager> void addManager(T manager) {
    	managers.put(manager.getClass(), manager);
    }

    public <T extends Manager> T getManager(Class<T> type) {
        return type.cast(managers.get(type));
    }
}