package com.example.projectmanagementbe.api.services.impls.attandance;

import com.example.projectmanagementbe.api.models.employee.CaptureDatum;
import com.example.projectmanagementbe.api.repositories.attandance.CaptureDatumRepository;
import com.example.projectmanagementbe.api.services.attandance.IDataMigrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataMigrationServiceImpl implements IDataMigrationService {

  private final CaptureDatumRepository employeeRepository;
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public DataMigrationServiceImpl(CaptureDatumRepository employeeRepository) {
    this.employeeRepository = employeeRepository;

    String url = "jdbc:sqlserver://10.11.132.109:1433;databaseName=ivms;encrypt=true;trustServerCertificate=true";
    String username = "sa";
    String password = "root";

    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);

    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  @Scheduled(cron = "0 */60 * * * ?")
  public void migrateEmployees() {
    // capture_id is unreliable as a sync cursor: legacy rows written before this field
    // existed are all NULL, which would make MAX(captureId) restart the cursor at 0 and
    // re-pull the entire attlogs history on every run. The "time" column has always been
    // populated, so use the latest already-synced timestamp as the cursor instead.
    String maxTime = employeeRepository.findMaxTime();
    Timestamp lastSyncedTime;
    try {
      lastSyncedTime = maxTime != null ? Timestamp.valueOf(maxTime) : Timestamp.valueOf("1970-01-01 00:00:00");
    } catch (IllegalArgumentException e) {
      lastSyncedTime = Timestamp.valueOf("1970-01-01 00:00:00");
    }

    String sql = "SELECT [id]\n" +
            "      ,[employeeID]\n" +
            "      ,[personName]\n" +
            "      ,[authDateTime]\n" +
            "      ,[authTime]\n" +
            "      ,[authDate]\n" +
            "      ,[direction]\n" +
            "      ,[deviceName]\n" +
            "      ,[cardNo]\n" +
            "      ,[deviceSN]\n" +
            "  FROM [ivms].[dbo].[attlogs]\n" +
            " WHERE [authDateTime] > ?\n" +
            " ORDER BY [authDateTime] ASC;";

    List<CaptureDatum> newRecords = jdbcTemplate.query(sql, (rs, rowNum) -> {
      CaptureDatum emp = new CaptureDatum();
      emp.setCaptureId(rs.getLong("id"));
      emp.setDeviceId(rs.getString("deviceName"));
      emp.setTime(rs.getString("authDateTime"));
      emp.setMatchStatus(rs.getString("direction"));
      emp.setPersonId(rs.getString("employeeID"));
      emp.setPersonName(rs.getString("personName"));
      emp.setExistMask(rs.getString("cardNo"));
      emp.setDeviceSn(rs.getString("deviceSN"));
      return emp;
    }, lastSyncedTime);

    List<CaptureDatum> toSave = newRecords.stream()
            .filter(emp -> !employeeRepository.existsByCaptureId(emp.getCaptureId()))
            .toList();

    employeeRepository.saveAll(toSave);

    System.out.println("Đã đồng bộ " + toSave.size() + " bản ghi chấm công mới từ iVMS.");
  }
}