package com.gyvex.framework.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gyvex.framework.database.Model;
import com.gyvex.framework.models.MigrationModel;

public class MigrationRepository {
    @SuppressWarnings("unchecked")
	public static List<MigrationModel> getExecutedMigrations() {
        return MigrationModel.all(MigrationModel.class);
    }
    
    @SuppressWarnings("unchecked")
	public static MigrationModel addMigration(String migrationName) {
        try {
            Map<String, Object> values = new HashMap<>();
            values.put("migration_name", migrationName);
            values.put("executed", 1);

            return Model.create(MigrationModel.class, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
		return null;
    }
}
