package com.example.projectmanagementbe.api.models.dto.responses.project;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MediaResponse {

  private String id;

  private String type;

  private String path;

  private String entryBy;

  private LocalDateTime entryDate;

  private String updatedBy;

  private LocalDateTime updatedDate;
}
