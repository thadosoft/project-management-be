package com.example.projectmanagementbe.api.models.dto.requests.project;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class MediaRequest {

  private String projectName;

  private MultipartFile file;

  private String assignmentId;

  private boolean isContent;

  private String fileName;

  public MediaRequest(String projectName, MultipartFile file, String assignmentId, boolean isContent) {
    this.projectName = projectName;
    this.file = file;
    this.assignmentId = assignmentId;
    this.isContent = isContent;
  }
}
