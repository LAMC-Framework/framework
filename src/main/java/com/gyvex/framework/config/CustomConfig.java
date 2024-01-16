package com.gyvex.framework.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gyvex.framework.LAMC;
import com.gyvex.framework.log.LogManager;

public class CustomConfig {
    private File file;
    private FileConfiguration config;

    public CustomConfig(String filename) {
        File dataFolder = LAMC.getInstance().getPlugin().getDataFolder();
        this.file = new File(dataFolder, filename + ".yml");

        if (!this.file.exists()) {
            this.file.getParentFile().mkdirs();
            LAMC.getInstance().getPlugin().saveResource(filename + ".yml", false);
        }

        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public void saveConfig() {
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            LogManager.warning("Could not save configuration " + this.file.getName());
        }
    }

    public void reloadConfig() {
        if (this.file.exists()) {
            this.config = YamlConfiguration.loadConfiguration(this.file);
        }
    }
}
