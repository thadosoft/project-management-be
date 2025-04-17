package com.example.projectmanagementbe.api.controllers;

import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.AttendanceByPeriodResponse;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.CreateAttendanceResponse;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.PeriodResponse;
import com.example.projectmanagementbe.api.services.attandance.IPeriodService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/periods")
@AllArgsConstructor
public class PeriodController {

  private final IPeriodService periodService;

  @GetMapping
  public ResponseEntity<List<PeriodResponse>> getMonthlyAttendance() {
    return ResponseEntity.ok(periodService.getPeriodResponses());
  }

  @GetMapping("/attandance-detail/{id}")
  public ResponseEntity<List<AttendanceByPeriodResponse>> CreateMonthlyAttendance(
      @PathVariable Long id) {
    return ResponseEntity.ok(periodService.getAttandanceByPeriodId(id));
  }
}
