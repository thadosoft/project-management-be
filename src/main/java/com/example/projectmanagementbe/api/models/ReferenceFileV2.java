package com.example.projectmanagementbe.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.example.projectmanagementbe.api.models.iventory.InventoryItem;
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
@Table(name = "reference_file_v2")
public class ReferenceFileV2 extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "inventory_item_id", nullable = false)
  @JsonBackReference
  private InventoryItem inventoryItem;

  @Size(max = 255)
  @Column(name = "file_name", nullable = false)
  private String fileName;

  @Size(max = 100)
  @Column(name = "file_type", length = 100)
  private String fileType;

  @Column(name = "file_size", nullable = false)
  private Long fileSize;

  @Size(max = 512)
  @Column(name = "file_path", nullable = false, length = 512)
  private String filePath;
}