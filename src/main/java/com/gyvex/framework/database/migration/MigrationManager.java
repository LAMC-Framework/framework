package com.gyvex.framework.database.migration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.gyvex.framework.manager.Manager;
import com.gyvex.framework.models.MigrationModel;
import com.gyvex.framework.repositories.MigrationRepository;

public class MigrationManager extends Manager {
    private List<Migration> migrations;
    private List<MigrationModel> executedMigrations;

    public MigrationManager() {
        this.migrations = new ArrayList<>();
        this.executedMigrations = MigrationRepository.getExecutedMigrations();
    }

    public void loadMigrations(List<Migration> migrations) {
    	this.migrations = migrations;
    }

    public void migrate() {
        this.sortMigrationsByName();

        for (Migration migration : this.migrations) {
            String migrationName = migration.getMigrationName();
            boolean isExecuted = executedMigrations.stream()
                    .anyMatch(model -> model.getAttribute("migration_name").equals(migrationName));

            if (!isExecuted) {
                migration.up();
                MigrationModel migrationModel = MigrationRepository.addMigration(migrationName);
                this.executedMigrations.add(migrationModel);
            }
        }
    }

    public void rollback() {
        for (Migration migration : this.migrations) {
            migration.down();
        }

        this.migrations.clear();
        this.executedMigrations.clear();
    }

    public void sortMigrationsByName() {
        Collections.sort(migrations, Comparator.comparing(Migration::getMigrationName));
    }
}
