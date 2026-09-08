package com.example.projectmanagementbe.api.services;

import com.example.projectmanagementbe.api.models.LeaveBalance;
import com.example.projectmanagementbe.api.models.dto.requests.LeaveBalance.UpdateLeaveBalanceRequest;
import com.example.projectmanagementbe.api.models.dto.responses.LeaveBalanceResponse;
import java.util.List;

public interface LeaveBalanceService {

  /** Returns the balance for the employee/year, creating a default 12-day row if missing. */
  LeaveBalance getOrCreate(Long employeeId, int year);

  /** Remaining days (entitled - used) without persisting; defaults to 12 when no row exists. */
  double getRemaining(Long employeeId, int year);

  LeaveBalanceResponse getMyBalance(Integer year);

  List<LeaveBalanceResponse> list(Integer year, Long employeeId);

  LeaveBalanceResponse update(Long employeeId, Integer year, UpdateLeaveBalanceRequest request);

  /** Adds {@code days} to used, rejecting if it would push remaining below -3. */
  void applyLeave(Long employeeId, int year, double days);

  /** Gives {@code days} back to the balance (approved request cancelled/rejected). */
  void revertLeave(Long employeeId, int year, double days);
}
