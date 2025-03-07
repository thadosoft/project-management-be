package com.example.projectmanagementbe.api.models.dto.requests.timekeeping;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCaptureDatumRequest {

  private String personName;

  private LocalDateTime startDate;

  private LocalDateTime endDate;
}
