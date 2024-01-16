package com.gyvex.framework;

import java.util.Collections;
import java.util.List;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.gyvex.framework.command.Command;
import com.gyvex.framework.database.migration.Migration;

public abstract class LAMCPlugin extends JavaPlugin {
    private Kernel kernel;

    @Override
    public void onEnable() {
        super.onEnable();

        if (LAMC.getInstance() == null) {
            new LAMC(this);
        }

        this.kernel = new Kernel();
        this.kernel.load();

        onLAMCEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        
        onLAMCDisable();
    }

    protected abstract void onLAMCEnable();

    protected abstract void onLAMCDisable();

    public List<Command> commands() {
        return Collections.emptyList();
    }
    
    public List<Listener> listeners() {
        return Collections.emptyList();
    }
    
    public List<String> configs() {
        return Collections.emptyList();
    }
    
    public List<Migration> migrations() {
        return Collections.emptyList();
    }

    public Kernel getKernel() {
        return kernel;
    }
}
