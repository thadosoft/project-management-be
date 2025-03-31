package com.example.projectmanagementbe.api.services.impls.attandance;

import com.example.projectmanagementbe.api.mappers.AttandanceMapper;
import com.example.projectmanagementbe.api.models.Attendance;
import com.example.projectmanagementbe.api.models.Period;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.CreateAttendanceResponse;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.DailyAttendance;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.UpdateDailyAttendance;
import com.example.projectmanagementbe.api.models.employee.Employee;
import com.example.projectmanagementbe.api.repositories.PeriodRepository;
import com.example.projectmanagementbe.api.repositories.attandance.AttendanceRepository;
import com.example.projectmanagementbe.api.services.attandance.IAttendanceService;
import com.example.projectmanagementbe.exception.ErrorCode;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements IAttendanceService {

  private final AttendanceRepository attendanceRepository;
  private final PeriodRepository periodRepository;
  private final AttandanceMapper mapper;

  @Override
  @Transactional
  public List<CreateAttendanceResponse> getAttendanceForMonth(int year, int month) {
    List<Object[]> results = attendanceRepository.getAttendanceByDate(year, month);

    Map<String, CreateAttendanceResponse> attendanceMap = new HashMap<>();
    for (Object[] row : results) {
      String employeeCode = (String) row[0];
      String fullName = (String) row[1];
      LocalDate workDate = ((Date) row[2]).toLocalDate();
      String shiftName = (String) row[3];
      BigDecimal totalShift = (BigDecimal) row[4];
      String checkInTime = (String) row[5];
      String checkOutTime = (String) row[6];
      String otherCheckins = (String) row[7];

      CreateAttendanceResponse attendance = attendanceMap.computeIfAbsent(employeeCode,
          key -> new CreateAttendanceResponse(employeeCode, fullName, null, null, null, null, new ArrayList<>()));

      attendance.getDailyAttendance().add(new DailyAttendance(workDate, shiftName, totalShift, checkInTime, checkOutTime, otherCheckins));
    }

    return new ArrayList<>(attendanceMap.values());
  }

  @Override
  @Transactional
  public void CreateAttendanceForMonth(int year, int month) {
    if (attendanceRepository.countByYearAndMonth(year, month) > 0) {
      throw new IllegalStateException("Dữ liệu chấm công cho tháng " + month + "/" + year + " đã tồn tại.");
    }

    Period period = new Period();

    if (periodRepository.findByMonthAndYear(month, year) == null) {
      period.setMonth(month);
      period.setYear(year);
      period.setStartDate(Date.valueOf(YearMonth.of(year, month).atDay(1)));
      period.setEndDate(Date.valueOf(YearMonth.of(year, month).atEndOfMonth()));
      periodRepository.save(period);
    } else {
      period = periodRepository.findByMonthAndYear(month, year);
    }

    List<Object[]> results = attendanceRepository.getAttendanceByDate(year, month);

    for (Object[] row : results) {
      String employeeCode = (String) row[0];
      String fullName = (String) row[1];
      LocalDate workDate = ((Date) row[2]).toLocalDate();
      String shiftName = (String) row[3];
      BigDecimal totalShift = (BigDecimal) row[4];
      String checkInTime = (String) row[5];
      String checkOutTime = (String) row[6];
      String otherCheckins = (String) row[7];

      Attendance attendance = new Attendance();
      attendance.setEmployeeCode(employeeCode);
      attendance.setFullName(fullName);
      attendance.setWorkDate(workDate);
      attendance.setShiftName(shiftName);
      attendance.setTotalShifts(totalShift);
      attendance.setMorningCheckin(checkInTime);
      attendance.setAfternoonCheckout(checkOutTime);
      attendance.setOtherCheckins(otherCheckins);
      attendance.setPeriodId(period);

      attendanceRepository.save(attendance);
    }
  }

  @Override
  public void update(Long id, UpdateDailyAttendance attendance) {

    System.out.println("Id: "+ id);
    System.out.println("Id: "+ attendance.getFullName());

    Attendance module = attendanceRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.ATTENDANCE_NOT_FOUND.toString()));

    mapper.update(attendance, module);

    attendanceRepository.save(module);
  }
}
