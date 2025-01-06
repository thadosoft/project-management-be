package com.example.projectmanagementbe.dto.responses;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
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

  private LocalDateTime modifiedDate;

  private LocalDateTime createdDate;
}
