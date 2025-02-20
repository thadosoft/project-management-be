package com.example.projectmanagementbe.api.models.project;

import com.example.projectmanagementbe.api.models.Base;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "tasks", schema = "project_management_db")
public class Task extends Base {

  @NotBlank(message = "Task status cannot be blank")
  @Column(name = "status")
  private String status;

  @Min(value = 1, message = "Task order cannot be less than one")
  @Column(name = "task_order")
  private int taskOrder;

  @NotNull(message = "Project cannot be null")
  @ManyToOne
  @JoinColumn(name = "project_id")
  private Project project;

  @OneToMany(mappedBy = "task")
  private List<Assignment> assignments;
}
