package com.example.projectmanagementbe.dto.requests.updates;

import com.example.projectmanagementbe.enums.MediaType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MediaUpdateRequest {

  private Long assignmentId;

  private MediaType type;

  private String path;
}
