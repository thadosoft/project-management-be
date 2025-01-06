package com.example.projectmanagementbe.entities;

import com.example.projectmanagementbe.enums.MediaType;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "medias", schema = "project_management_db")
public class MediaEntity extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", columnDefinition = "CHAR(36)")
  private String id = UUID.randomUUID().toString();

  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private MediaType type;

  @Column(name = "path")
  private String path;

  @ManyToOne
  @JoinColumn(name = "assignment_id")
  private AssignmentEntity assignment;
}
