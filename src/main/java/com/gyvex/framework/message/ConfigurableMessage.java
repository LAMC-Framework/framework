package com.gyvex.framework.message;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.gyvex.framework.LAMC;
import com.gyvex.framework.config.ConfigManager;
import com.gyvex.framework.log.LogManager;

public class ConfigurableMessage {
    public static void sendMessage(CommandSender player, String messageKey) {
    	if (player instanceof Player) {
    		player = (Player) player;
            ConfigManager configManager = LAMC.getInstance().getConfigManager();
            FileConfiguration messagesConfig = configManager.getCustomConfig("messages").getConfig();
            
            if (messagesConfig == null) {
            	LogManager.severe("Could not find messages.yml");
            	return;
            }
            
            String message = ChatColor.translateAlternateColorCodes('&', messagesConfig.getString(messageKey, "&cMessage not found: " + messageKey));
            
            player.sendMessage(message);
            return;
    	}
    	
    	player.sendMessage(messageKey);
    }
}
