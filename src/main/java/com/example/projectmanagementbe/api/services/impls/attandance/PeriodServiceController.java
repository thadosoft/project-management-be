package com.example.projectmanagementbe.api.services.impls.attandance;

import com.example.projectmanagementbe.api.mappers.PeriodMapper;
import com.example.projectmanagementbe.api.models.Attendance;
import com.example.projectmanagementbe.api.models.Period;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.AttendanceByPeriodResponse;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.CreateAttendanceResponse;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.DailyAttendance;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.DailyAttendanceByPeriod;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.PeriodResponse;
import com.example.projectmanagementbe.api.repositories.PeriodRepository;
import com.example.projectmanagementbe.api.repositories.attandance.AttendanceRepository;
import com.example.projectmanagementbe.api.services.attandance.IPeriodService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PeriodServiceController implements IPeriodService {

  private final PeriodRepository periodRepository;
  private final PeriodMapper periodMapper;
  private final AttendanceRepository attendanceRepository;

  @Override
  public List<PeriodResponse> getPeriodResponses() {
    return periodMapper.mapPeriodResponses(periodRepository.findAll());
  }

  @Override
  public List<AttendanceByPeriodResponse> getAttandanceByPeriodId(Long id) {

    Period period = periodRepository.findById(id).get();

    List<Attendance> results = attendanceRepository.findAllByPeriodId(period);

    Map<String, AttendanceByPeriodResponse> attendanceMap = new HashMap<>();
    for (Attendance attendance : results) {
      Long attendanceId = attendance.getId();
      String employeeCode = attendance.getEmployeeCode();
      String fullName = attendance.getFullName();
      LocalDate workDate = attendance.getWorkDate();
      String shiftName = attendance.getShiftName();
      BigDecimal totalShift = attendance.getTotalShifts();
      String checkInTime = attendance.getMorningCheckin();
      String checkOutTime = attendance.getAfternoonCheckout();
      String otherCheckins = attendance.getOtherCheckins();

      AttendanceByPeriodResponse createAttendanceResponse = attendanceMap.computeIfAbsent(employeeCode,
          key -> new AttendanceByPeriodResponse(attendanceId, employeeCode, fullName, period.getMonth(), period.getYear(), period.getStartDate(), period.getEndDate(), new ArrayList<>()));

      createAttendanceResponse.getDailyAttendance().add(new DailyAttendanceByPeriod(attendanceId, workDate, shiftName, totalShift, checkInTime, checkOutTime, otherCheckins));
    }

    return new ArrayList<>(attendanceMap.values());
  }
}
