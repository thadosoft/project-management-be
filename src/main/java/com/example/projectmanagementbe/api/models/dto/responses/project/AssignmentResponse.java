package com.example.projectmanagementbe.api.models.dto.responses.project;

import com.example.projectmanagementbe.api.models.dto.responses.UserResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.projectmanagementbe.auth.enums.AssignmentStatus;
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

  private LocalDateTime start_date;
  private LocalDateTime end_date;

  private AssignmentStatus status_type;
}
