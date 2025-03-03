package com.example.projectmanagementbe.api.models;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class Base {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", columnDefinition = "CHAR(36)")
  private String id;

  @Column(name = "entry_by", columnDefinition = "CHAR(36)")
  private String entryBy;

  @Column(name = "entry_date", nullable = false, updatable = false)
  @CreatedDate
  private LocalDateTime entryDate;

  @Column(name = "updated_by", columnDefinition = "CHAR(36)")
  private String updatedBy;

  @Column(name = "updated_date", nullable = false)
  @LastModifiedDate
  private LocalDateTime updatedDate;
}