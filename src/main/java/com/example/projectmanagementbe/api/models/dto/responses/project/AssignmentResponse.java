package com.example.projectmanagementbe.api.models.dto.responses.project;

import com.example.projectmanagementbe.api.models.dto.responses.UserResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentResponse {

  private String id;

  private String title;

  private String description;

  private String status;

  private int assignmentOrder;

  private TaskResponse task;

  private UserResponse assigner;

  private UserResponse receiver;

  private List<MediaResponse> medias;

  private String entryBy;

  private LocalDateTime entryDate;

  private String updatedBy;

  private LocalDateTime updatedDate;
}
