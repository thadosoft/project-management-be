package com.example.projectmanagementbe.api.models.dto.requests.timekeeping;

import java.util.Date;
import lombok.Data;

@Data
public class AttendanceRequest {

  private String date;

  private String empCode;
}