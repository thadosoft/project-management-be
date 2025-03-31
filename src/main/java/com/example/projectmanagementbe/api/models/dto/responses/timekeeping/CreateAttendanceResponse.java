package com.example.projectmanagementbe.api.models.dto.responses.timekeeping;

import java.sql.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAttendanceResponse {

  private String employeeCode;

  private String fullName;

  private Integer month;

  private Integer year;

  private Date startDate;

  private Date endDate;

  private List<DailyAttendance> dailyAttendance;
}

