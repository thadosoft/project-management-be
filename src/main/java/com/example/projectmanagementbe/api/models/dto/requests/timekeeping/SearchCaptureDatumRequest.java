package com.example.projectmanagementbe.api.models.dto.requests.timekeeping;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCaptureDatumRequest {

  private String personName;

  private String startDate;

  private String endDate;
}
