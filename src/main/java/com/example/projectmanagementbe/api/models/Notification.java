package com.example.projectmanagementbe.api.models;

import com.example.projectmanagementbe.api.enums.NotificationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "notifications")
public class Notification extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "recipient_id", nullable = false, columnDefinition = "CHAR(36)")
  private String recipientId;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private NotificationType type;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "message", length = 1000)
  private String message;

  @Column(name = "reference_id")
  private Long referenceId;

  @Column(name = "is_read", nullable = false)
  private Boolean isRead = false;
}
