package com.gyvex.framework.database.migration;

public interface Migration {
    public void up();
    
    public void down();
    
    public String getMigrationName();
}
