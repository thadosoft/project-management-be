package com.example.projectmanagementbe.api.controllers;

import com.example.projectmanagementbe.api.models.dto.requests.quotation.UpdateQuotationRequest;
import com.example.projectmanagementbe.api.models.dto.requests.timekeeping.AttendanceRequest;
import com.example.projectmanagementbe.api.models.dto.requests.timekeeping.SearchCaptureDatumRequest;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.AttendanceResponse;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.CaptureDatumResponse;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.CreateAttendanceResponse;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.UpdateDailyAttendance;
import com.example.projectmanagementbe.api.services.attandance.IAttendanceService;
import com.example.projectmanagementbe.api.services.attandance.ICaptureDatumService;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/attendances")
@AllArgsConstructor
public class AttendanceController {

  private final ICaptureDatumService iCaptureDatumService;
  private final IAttendanceService attendanceService;

  @PostMapping("/search")
  public Page<CaptureDatumResponse> search(@RequestBody SearchCaptureDatumRequest request, Pageable pageable) {
    return iCaptureDatumService.searchByParams(request, pageable);
  }

  @PostMapping
  public ResponseEntity<BigDecimal> getAttendance(
      @RequestBody AttendanceRequest request) {

    BigDecimal attendanceList = iCaptureDatumService.getAttendance(request);
    return ResponseEntity.ok(attendanceList);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdateDailyAttendance request) {
    attendanceService.update(id, request);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/monthly")
  public ResponseEntity<List<CreateAttendanceResponse>> getMonthlyAttendance(
      @RequestParam int year,
      @RequestParam int month) {
    List<CreateAttendanceResponse> attendanceList = attendanceService.getAttendanceForMonth(year, month);
    return ResponseEntity.ok(attendanceList);
  }

  @PostMapping("/create/monthly")
  public ResponseEntity<List<CreateAttendanceResponse>> CreatetMonthlyAttendance(
      @RequestParam int year,
      @RequestParam int month) {
    attendanceService.CreateAttendanceForMonth(year, month);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}