package com.example.projectmanagementbe.api.models.referenceProfile;

import com.example.projectmanagementbe.api.models.Auditable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "reference_profile")
public class ReferenceProfile extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Size(max = 255)
  @NotNull
  @Column(name = "name", nullable = false)
  private String name;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "module_id", nullable = false)
  @JsonBackReference
  private Modules module;

  @Column(name = "description")
  private String description;

  @OneToMany(mappedBy = "referenceProfile", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<ReferenceFile> referenceFiles;

  @OneToMany(mappedBy = "referenceProfile", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<ReferenceLink> referenceLinks;
}
