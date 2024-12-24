package com.example.projectmanagementbe.dto.requests.creates;

import com.example.projectmanagementbe.enums.MediaType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MediaCreateRequest {

  private Long assignmentId;

  private MediaType type;

  private String path;
}
