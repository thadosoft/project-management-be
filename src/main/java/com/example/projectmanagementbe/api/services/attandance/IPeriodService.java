package com.example.projectmanagementbe.api.services.attandance;

import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.AttendanceByPeriodResponse;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.CreateAttendanceResponse;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.PeriodResponse;
import java.util.List;

public interface IPeriodService {

  List<PeriodResponse> getPeriodResponses();

  List<AttendanceByPeriodResponse> getAttandanceByPeriodId(Long id);
}
