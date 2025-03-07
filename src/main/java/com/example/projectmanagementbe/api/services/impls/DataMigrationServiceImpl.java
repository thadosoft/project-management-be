package com.example.projectmanagementbe.api.services.impls;

import com.example.projectmanagementbe.api.models.employee.CaptureDatum;
import com.example.projectmanagementbe.api.repositories.CaptureDatumRepository;
import com.example.projectmanagementbe.api.services.Timekeeping.IDataMigrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

    String url = "jdbc:sqlite:D:/FaceRASystemTool/huaanDatabase.sqlite";
    DataSource sqliteDataSource = new org.sqlite.SQLiteDataSource();
    ((org.sqlite.SQLiteDataSource) sqliteDataSource).setUrl(url);
    this.jdbcTemplate = new JdbcTemplate(sqliteDataSource);
  }

  @Override
  @Scheduled(cron = "0 0 0 * * ?")
  public void migrateEmployees() {
    String sql = "SELECT id, sequnce, device_id, addr_name, time, match_status, match_type, " +
        "person_id, person_name, hatColor, wg_card_id, match_failed_reson, exist_mask, " +
        "body_temp, device_sn, idcard_number, idcard_name, closeup, QRcodestatus, QRcode, trip_infor " +
        "FROM Capture_Data";

    List<CaptureDatum> employees = jdbcTemplate.query(sql, new RowMapper<CaptureDatum>() {
      @Override
      public CaptureDatum mapRow(ResultSet rs, int rowNum) throws SQLException {
        CaptureDatum emp = new CaptureDatum();
        emp.setCaptureId(rs.getLong("id"));
        emp.setSequnce(rs.getString("sequnce"));
        emp.setDeviceId(rs.getString("device_id"));
        emp.setAddrName(rs.getString("addr_name"));
        emp.setTime(rs.getString("time"));
        emp.setMatchStatus(rs.getString("match_status"));
        emp.setMatchType(rs.getString("match_type"));
        emp.setPersonId(rs.getString("person_id"));
        emp.setPersonName(rs.getString("person_name"));
        emp.setHatColor(rs.getString("hatColor"));
        emp.setWgCardId(rs.getString("wg_card_id"));
        emp.setMatchFailedReson(rs.getString("match_failed_reson"));
        emp.setExistMask(rs.getString("exist_mask"));
        emp.setBodyTemp(rs.getString("body_temp"));
        emp.setDeviceSn(rs.getString("device_sn"));
        emp.setIdcardNumber(rs.getString("idcard_number"));
        emp.setIdcardName(rs.getString("idcard_name"));
        emp.setCloseup(rs.getString("closeup"));
        emp.setQRcodestatus(rs.getString("QRcodestatus"));
        emp.setQRcode(rs.getString("QRcode"));
        emp.setTripInfor(rs.getString("trip_infor"));

        return emp;
      }
    });

    for (CaptureDatum emp : employees) {
      if (!employeeRepository.existsByCaptureId(emp.getCaptureId())) {
        employeeRepository.save(emp);
      }
    }

    System.out.println("Dữ liệu đã được chuyển từ SQLite sang MySQL thành công!");
  }
}