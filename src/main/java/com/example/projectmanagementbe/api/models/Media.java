package com.example.projectmanagementbe.api.models;

import com.example.projectmanagementbe.api.enums.MediaType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "medias", schema = "project_management_db")
public class Media extends Base {

  @NotBlank(message = "Media type cannot be blank")
  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private MediaType type;

  @NotBlank(message = "Media path cannot be blank")
  @Column(name = "path")
  private String path;

  @NotNull(message = "Assignment cannot be null")
  @ManyToOne
  @JoinColumn(name = "assignment_id")
  private Assignment assignment;
}
