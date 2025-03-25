package com.example.projectmanagementbe.api.models.dto.responses.timekeeping;

import java.math.BigDecimal;
import java.sql.Date;
import lombok.Data;

@Data
public class AttendanceResponse {

  private String employeeCode;

  private String fullName;

  private Date workDate;

  private BigDecimal totalShifts;
}