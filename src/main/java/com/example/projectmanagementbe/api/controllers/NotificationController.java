package com.example.projectmanagementbe.api.controllers;

import com.example.projectmanagementbe.api.models.dto.responses.NotificationResponse;
import com.example.projectmanagementbe.api.services.NotificationService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

  private final NotificationService notificationService;

  @GetMapping("/me")
  public Page<NotificationResponse> getMine(Pageable pageable) {
    return notificationService.getMine(pageable);
  }

  @GetMapping("/me/unread-count")
  public Map<String, Long> unreadCount() {
    return Map.of("count", notificationService.unreadCount());
  }

  @PatchMapping("/{id}/read")
  public ResponseEntity<Void> markRead(@PathVariable Long id) {
    notificationService.markRead(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/me/read-all")
  public ResponseEntity<Void> markAllRead() {
    notificationService.markAllRead();
    return ResponseEntity.noContent().build();
  }
}
