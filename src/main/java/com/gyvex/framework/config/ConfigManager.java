package com.gyvex.framework.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gyvex.framework.manager.Manager;

public class ConfigManager extends Manager {
    private Map<String, CustomConfig> customConfigs;
    
    public ConfigManager() {
    	this.customConfigs = new HashMap<>();
    }
	
    public ConfigManager(List<String> configNames) {
    	this.customConfigs = new HashMap<>();
    	
        for (String name : configNames) {
            CustomConfig customConfig = new CustomConfig(name);
            customConfigs.put(name, customConfig);
        }
    }
    
    public void setupConfig(String configName) {
        CustomConfig customConfig = new CustomConfig(configName);
        customConfigs.put(configName, customConfig);
    }

    public CustomConfig getCustomConfig(String name) {
        return customConfigs.get(name);
    }
}
