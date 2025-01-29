package com.example.projectmanagementbe.api.models.dto.requests;

import com.example.projectmanagementbe.api.enums.MediaType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MediaRequest {

  @NotBlank(message = "Media type cannot be blank")
  private MediaType type;

  @NotBlank(message = "Media path cannot be blank")
  private String path;

  @NotNull(message = "Assignment cannot be null")
  private String assignmentId;
}
