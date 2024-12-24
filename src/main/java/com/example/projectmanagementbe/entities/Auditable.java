package com.example.projectmanagementbe.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
@EnableJpaAuditing
public abstract class Auditable {

  @Column(name = "created_date", nullable = false, insertable = false, updatable = false)
  @CreatedDate
  protected LocalDateTime createdDate;

  @Column(name = "modified_date", nullable = false, insertable = false)
  @LastModifiedDate
  protected LocalDateTime modifiedDate;
}
