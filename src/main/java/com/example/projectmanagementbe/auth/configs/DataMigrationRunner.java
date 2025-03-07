package com.example.projectmanagementbe.auth.configs;

import com.example.projectmanagementbe.api.services.Timekeeping.IDataMigrationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataMigrationRunner implements CommandLineRunner {

  private final IDataMigrationService IDataMigrationService;

  public DataMigrationRunner(IDataMigrationService IDataMigrationService) {
    this.IDataMigrationService = IDataMigrationService;
  }

  @Override
  public void run(String... args) throws Exception {
    IDataMigrationService.migrateEmployees();
  }
}
