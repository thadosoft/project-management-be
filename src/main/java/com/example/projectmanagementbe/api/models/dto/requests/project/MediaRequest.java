package com.example.projectmanagementbe.api.models.dto.requests.project;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MediaRequest {

  @NotNull(message = "Project name cannot be null")
  private String projectName;

  private MultipartFile file;

  @NotNull(message = "Assignment cannot be null")
  private String assignmentId;

  private boolean isContent;

  private String fileName;

  public MediaRequest(String projectName, String assignmentId, String fileName) {
    this.projectName = projectName;
    this.assignmentId = assignmentId;
    this.fileName = fileName;
  }

  public MediaRequest(String projectName, MultipartFile file, String assignmentId, boolean isContent) {
    this.projectName = projectName;
    this.file = file;
    this.assignmentId = assignmentId;
    this.isContent = isContent;
  }
}
