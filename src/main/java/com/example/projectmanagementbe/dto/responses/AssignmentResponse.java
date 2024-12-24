package com.example.projectmanagementbe.dto.responses;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentResponse {

  private Long id;

  private String title;

  private String description;

  private UserResponse assigner;

  private UserResponse receiver;

  private List<MediaResponse> medias;

  private LocalDateTime modifiedDate;

  private LocalDateTime createdDate;
}
