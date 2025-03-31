package com.example.projectmanagementbe.api.models.dto.responses.timekeeping;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDailyAttendance {

  private Long id;

  private String employeeCode;

  private String fullName;

  private LocalDate workDate;

  private String shiftName;

  private BigDecimal totalShifts;

  private String morningCheckin;

  private String afternoonCheckout;

  private String otherCheckins;
}
