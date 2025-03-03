package com.example.projectmanagementbe.api.models;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDate;

import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@EnableJpaAuditing
public abstract class Auditable {

  @Column(name = "created_at", nullable = false, updatable = false)
  @CreatedDate
  protected LocalDateTime createdAt;

  @Column(name = "updated_at")
  @LastModifiedDate
  protected LocalDateTime updatedAt;
}
