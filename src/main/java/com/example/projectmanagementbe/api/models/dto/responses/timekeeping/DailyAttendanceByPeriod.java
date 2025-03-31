package com.example.projectmanagementbe.api.models.dto.responses.timekeeping;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DailyAttendanceByPeriod {

  private Long id;

  private LocalDate workDate;

  private String shiftName;

  private BigDecimal totalShift;

  private String checkInTime;

  private String checkOutTime;

  private String otherCheckins;
}
