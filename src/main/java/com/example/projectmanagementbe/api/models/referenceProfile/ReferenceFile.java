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
@Table(name = "reference_files")
public class ReferenceFile extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "reference_profile_id", nullable = false)
  @JsonBackReference
  private ReferenceProfile referenceProfile;

  @Column(name = "file_data")
  private byte[] fileData;

  @Size(max = 255)
  @Column(name = "file_name")
  private String fileName;

  @Size(max = 100)
  @Column(name = "file_type", length = 100)
  private String fileType;

  @Column(name = "file_size")
  private Long fileSize;

  @Column(name = "file_url")
  private String fileUrl;
}