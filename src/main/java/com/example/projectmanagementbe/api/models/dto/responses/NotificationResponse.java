package com.example.projectmanagementbe.api.models.dto.responses;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResponse {

  private Long id;

  private String type;

  private String title;

  private String message;

  private Long referenceId;

  private Boolean isRead;

  private LocalDateTime createdAt;
}
