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
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataMigrationServiceImpl implements IDataMigrationService {

  private final CaptureDatumRepository employeeRepository;
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public DataMigrationServiceImpl(CaptureDatumRepository employeeRepository) {
    this.employeeRepository = employeeRepository;

    String url = "jdbc:sqlserver://192.168.1.104:1433;databaseName=ivms;encrypt=true;trustServerCertificate=true";
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
  @Scheduled(cron = "0 0 0 * * ?")
  public void migrateEmployees() {
    String sql = "SELECT [employeeID],\n" +
            "       [authDateTime],\n" +
            "       [authDate],\n" +
            "       [authTime],\n" +
            "       [direction],\n" +
            "       [deviceName],\n" +
            "       [deviceSN],\n" +
            "       [personName],\n" +
            "       [cardNo]\n" +
            "FROM [ivms].[dbo].[attlog];";

    List<CaptureDatum> employees = jdbcTemplate.query(sql, (rs, rowNum) -> {
      CaptureDatum emp = new CaptureDatum();
//      emp.setCaptureId(rs.getLong("id"));
//      emp.setSequnce(rs.getString("sequnce"));
      emp.setDeviceId(rs.getString("deviceName"));
//      emp.setAddrName(rs.getString("addr_name"));
      emp.setTime(rs.getString("authDateTime"));
      emp.setMatchStatus(rs.getString("direction"));
      emp.setMatchType(rs.getString("authDateTime"));
      emp.setPersonId(rs.getString("employeeID"));
      emp.setPersonName(rs.getString("personName"));
//      emp.setHatColor(rs.getString("hatColor"));
//      emp.setWgCardId(rs.getString("wg_card_id"));
//      emp.setMatchFailedReson(rs.getString("match_failed_reson"));
      emp.setExistMask(rs.getString("cardNo"));
//      emp.setBodyTemp(rs.getString("body_temp"));
      emp.setDeviceSn(rs.getString("deviceSN"));
//      emp.setIdcardNumber(rs.getString("idcard_number"));
//      emp.setIdcardName(rs.getString("idcard_name"));
//      emp.setCloseup(rs.getString("closeup"));
//      emp.setQRcodestatus(rs.getString("QRcodestatus"));
//      emp.setQRcode(rs.getString("QRcode"));
//      emp.setTripInfor(rs.getString("trip_infor"));
      return emp;
    });

    for (CaptureDatum emp : employees) {
//      if (!employeeRepository.existsByCaptureId(emp.getCaptureId())) {
        employeeRepository.save(emp);
//      }
    }

    System.out.println("Dữ liệu đã được chuyển thành SQL Server!");
  }
}