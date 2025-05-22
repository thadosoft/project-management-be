package com.example.projectmanagementbe.api.models.project;

import com.example.projectmanagementbe.api.models.Base;
import com.example.projectmanagementbe.auth.models.User;
import com.example.projectmanagementbe.auth.enums.AssignmentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "assignments", schema = "project_management_db")
public class Assignment extends Base {

  @NotBlank(message = "Assignment title cannot be blank")
  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Min(value = 1, message = "Assignment order cannot be less than one")
  @Column(name = "assignment_order")
  private int assignmentOrder;

  @ManyToOne
  @JoinColumn(name = "assigner_id")
  private User assigner;

  @ManyToOne
  @JoinColumn(name = "receiver_id")
  private User receiver;

  @NotNull(message = "Task cannot be null")
  @ManyToOne
  @JoinColumn(name = "task_id")
  private Task task;

  @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL)
  private List<Media> medias;

  @Enumerated(EnumType.STRING)
  @Column(name = "status_type")
  private AssignmentStatus status_type;

  @JsonFormat(pattern = "dd/MM/yyyy")
  @Column(name = "start_date")
  private LocalDateTime start_date;

  @JsonFormat(pattern = "dd/MM/yyyy")
  @Column(name = "end_date")
  private LocalDateTime end_date;
}
