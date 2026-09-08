package com.example.projectmanagementbe.api.controllers;

import com.example.projectmanagementbe.api.models.dto.requests.LeaveBalance.UpdateLeaveBalanceRequest;
import com.example.projectmanagementbe.api.models.dto.responses.LeaveBalanceResponse;
import com.example.projectmanagementbe.api.services.LeaveBalanceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/leave-balances")
@RequiredArgsConstructor
public class LeaveBalanceController {

  private final LeaveBalanceService leaveBalanceService;

  @GetMapping("/me")
  public LeaveBalanceResponse getMine(@RequestParam(required = false) Integer year) {
    return leaveBalanceService.getMyBalance(year);
  }

  @GetMapping
  @PreAuthorize("hasAnyAuthority('OFM','ADMIN')")
  public List<LeaveBalanceResponse> list(@RequestParam(required = false) Integer year,
      @RequestParam(required = false) Long employeeId) {
    return leaveBalanceService.list(year, employeeId);
  }

  @PutMapping("/{employeeId}")
  @PreAuthorize("hasAuthority('OFM')")
  public LeaveBalanceResponse update(@PathVariable Long employeeId,
      @RequestParam(required = false) Integer year,
      @RequestBody UpdateLeaveBalanceRequest request) {
    return leaveBalanceService.update(employeeId, year, request);
  }
}
