package com.gyvex.framework.log;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import com.gyvex.framework.LAMC;
import com.gyvex.framework.manager.Manager;

public class LogManager extends Manager {
	private static final Logger pluginLogger = LAMC.getInstance().getPlugin().getLogger();

	public static void log(Level level, String message) {
		pluginLogger.log(level, message);
	}

	public static void info(String message) {
		log(Level.INFO, message);
	}

	public static void warning(String message) {
		log(Level.WARNING, message);
	}

	public static void severe(String message) {
		log(Level.SEVERE, message);
	}

	public static void errorAndDisable(String message) {
		severe(message);
		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.disablePlugin(LAMC.getInstance().getPlugin());
	}

	public static void errorAndDisable(String message, Exception exception) {
		severe(message);
		exception.printStackTrace();

		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.disablePlugin(LAMC.getInstance().getPlugin());
	}
}
