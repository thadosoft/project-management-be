package com.example.projectmanagementbe.api.repositories;

import com.example.projectmanagementbe.api.models.Notification;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

  Page<Notification> findByRecipientIdOrderByCreatedAtDesc(String recipientId, Pageable pageable);

  long countByRecipientIdAndIsReadFalse(String recipientId);

  Optional<Notification> findByIdAndRecipientId(Long id, String recipientId);

  @Modifying
  @Query("UPDATE Notification n SET n.isRead = true "
      + "WHERE n.recipientId = :recipientId AND n.isRead = false")
  int markAllRead(@Param("recipientId") String recipientId);
}
