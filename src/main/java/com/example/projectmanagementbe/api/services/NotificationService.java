package com.example.projectmanagementbe.api.services;

import com.example.projectmanagementbe.api.enums.NotificationType;
import com.example.projectmanagementbe.api.models.dto.responses.NotificationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

  void notifyUser(String recipientId, NotificationType type, String title, String message, Long referenceId);

  Page<NotificationResponse> getMine(Pageable pageable);

  long unreadCount();

  void markRead(Long id);

  void markAllRead();
}
