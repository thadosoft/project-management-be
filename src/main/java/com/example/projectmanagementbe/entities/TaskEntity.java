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
@Table(name = "tasks", schema = "project_management_db")
public class TaskEntity extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", columnDefinition = "CHAR(36)")
  private String id = UUID.randomUUID().toString();

  @Column(name = "status")
  private String status;

  @Column(name = "task_order")
  private int taskOrder;

  @ManyToOne
  @JoinColumn(name = "project_id")
  private ProjectEntity project;

  @OneToMany(mappedBy = "task")
  private List<AssignmentEntity> assignments;
}
