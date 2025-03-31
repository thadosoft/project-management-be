package com.example.projectmanagementbe.api.models.dto.responses.timekeeping;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeriodResponse {

  private Long id;

  private Integer month;

  private Integer year;

  private Date startDate;

  private Date endDate;

}
