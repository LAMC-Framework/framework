package com.gyvex.framework.manager;

import com.gyvex.framework.log.LogManager;

public abstract class Manager {
    public void logInitialization() {
        LogManager.info(this.getClass().getSimpleName() + " initialized.");
    }
}