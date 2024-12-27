package com.example.projectmanagementbe.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "assignments", schema = "project_management_db")
public class AssignmentEntity extends Auditable {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id")
  private Long id;

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
