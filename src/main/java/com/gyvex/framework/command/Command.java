package com.gyvex.framework.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gyvex.framework.LAMC;

public abstract class Command implements CommandExecutor {
	protected CommandSender sender;
    protected org.bukkit.command.Command command;
    protected String label;
    protected String[] args;
    
    public Command() {
    	LAMC.getInstance().getPlugin().getCommand(this.name()).setExecutor(this);
    }

    /**
     * Gets the name of the command.
     * @return The name of the command.
     */
    public abstract String name();

    /**
     * Method to execute the command.
     * @param sender Source of the command.
     * @param command Command which was executed.
     * @param label Alias of the command which was used.
     * @param args Passed command arguments.
     * @return true if a valid command, otherwise false.
     */
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        this.sender = sender;
        this.command = command;
        this.label = label;
        this.args = args;

        return execute();
    }

    /**
     * The logic to be executed when this command is run.
     * @return true if the command was executed successfully, otherwise false.
     */
    protected abstract boolean execute();
}
