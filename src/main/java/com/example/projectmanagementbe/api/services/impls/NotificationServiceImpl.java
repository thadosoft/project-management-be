package com.example.projectmanagementbe.api.services.impls;

import com.example.projectmanagementbe.api.enums.NotificationType;
import com.example.projectmanagementbe.api.models.Notification;
import com.example.projectmanagementbe.api.models.dto.responses.NotificationResponse;
import com.example.projectmanagementbe.api.repositories.NotificationRepository;
import com.example.projectmanagementbe.api.services.NotificationService;
import com.example.projectmanagementbe.exception.ApiRequestException;
import com.example.projectmanagementbe.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

  private final NotificationRepository notificationRepository;
  private final CurrentUserProvider currentUserProvider;

  @Override
  @Transactional
  public void notifyUser(String recipientId, NotificationType type, String title, String message,
      Long referenceId) {
    if (recipientId == null) {
      return;
    }
    Notification notification = new Notification();
    notification.setRecipientId(recipientId);
    notification.setType(type);
    notification.setTitle(title);
    notification.setMessage(message);
    notification.setReferenceId(referenceId);
    notification.setIsRead(false);
    notificationRepository.save(notification);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<NotificationResponse> getMine(Pageable pageable) {
    String userId = currentUserProvider.getCurrentUser().getId();
    return notificationRepository.findByRecipientIdOrderByCreatedAtDesc(userId, pageable)
        .map(this::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public long unreadCount() {
    String userId = currentUserProvider.getCurrentUser().getId();
    return notificationRepository.countByRecipientIdAndIsReadFalse(userId);
  }

  @Override
  @Transactional
  public void markRead(Long id) {
    String userId = currentUserProvider.getCurrentUser().getId();
    Notification notification = notificationRepository.findByIdAndRecipientId(id, userId)
        .orElseThrow(() -> new ApiRequestException(ErrorCode.NOTIFICATION_NOT_FOUND));
    notification.setIsRead(true);
    notificationRepository.save(notification);
  }

  @Override
  @Transactional
  public void markAllRead() {
    String userId = currentUserProvider.getCurrentUser().getId();
    notificationRepository.markAllRead(userId);
  }

  private NotificationResponse toResponse(Notification notification) {
    NotificationResponse response = new NotificationResponse();
    response.setId(notification.getId());
    response.setType(notification.getType() == null ? null : notification.getType().name());
    response.setTitle(notification.getTitle());
    response.setMessage(notification.getMessage());
    response.setReferenceId(notification.getReferenceId());
    response.setIsRead(notification.getIsRead());
    response.setCreatedAt(notification.getCreatedAt());
    return response;
  }
}
