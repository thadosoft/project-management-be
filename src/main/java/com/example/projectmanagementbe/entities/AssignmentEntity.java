package com.example.projectmanagementbe.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "assignments", schema = "project_management_db")
public class AssignmentEntity extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", columnDefinition = "CHAR(36)")
  private String id = UUID.randomUUID().toString();

  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "status")
  private String status;

  @Column(name = "assignment_order")
  private int assignmentOrder;

  @ManyToOne
  @JoinColumn(name = "assigner_id")
  private UserEntity assigner;

  @ManyToOne
  @JoinColumn(name = "receiver_id")
  private UserEntity receiver;

  @ManyToOne
  @JoinColumn(name = "task_id")
  private TaskEntity task;

  @OneToMany(mappedBy = "assignment")
  private List<MediaEntity> medias;
}
