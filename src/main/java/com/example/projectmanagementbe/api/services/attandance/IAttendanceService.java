package com.example.projectmanagementbe.api.services.attandance;

import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.AttendanceResponse;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.CreateAttendanceResponse;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.UpdateDailyAttendance;
import java.util.List;

public interface  IAttendanceService {
  List<CreateAttendanceResponse> getAttendanceForMonth(int year, int month);
  void CreateAttendanceForMonth(int year, int month);
  void update (Long id, UpdateDailyAttendance attendance);
}
