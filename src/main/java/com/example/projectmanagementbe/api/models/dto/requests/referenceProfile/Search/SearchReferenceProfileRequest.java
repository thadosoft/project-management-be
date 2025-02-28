package com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.Search;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchReferenceProfileRequest {

  private String name;

  private LocalDateTime startDate;

  private LocalDateTime endDate;
}
