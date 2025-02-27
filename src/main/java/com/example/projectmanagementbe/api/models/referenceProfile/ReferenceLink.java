package com.example.projectmanagementbe.api.models.referenceProfile;

import com.example.projectmanagementbe.api.models.Auditable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reference_links")
public class ReferenceLink extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "reference_profile_id", nullable = false)
  @JsonBackReference
  private ReferenceProfile referenceProfile;

  @Column(name = "link")
  private String link;

  @Size(max = 500)
  @Column(name = "description", length = 500)
  private String description;
}