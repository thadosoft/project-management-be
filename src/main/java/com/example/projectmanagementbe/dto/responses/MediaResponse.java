package com.example.projectmanagementbe.dto.responses;

import com.example.projectmanagementbe.enums.MediaType;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MediaResponse {

  private String id;

  private MediaType type;

  private String path;

  private LocalDateTime modifiedDate;

  private LocalDateTime createdDate;
}
