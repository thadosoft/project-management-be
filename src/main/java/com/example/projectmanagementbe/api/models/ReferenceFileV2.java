package com.example.projectmanagementbe.api.models;

import com.example.projectmanagementbe.api.models.iventory.InventoryItem;
import com.example.projectmanagementbe.api.models.Book;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reference_file_v2")
public class ReferenceFileV2 {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "inventory_item_id")
  @JsonBackReference
  private InventoryItem inventoryItem;

  @ManyToOne
  @JoinColumn(name = "book_id")
  @JsonBackReference
  private Book book;

  @Column(name = "file_name")
  private String fileName;

  @Column(name = "file_type")
  private String fileType;

  @Column(name = "file_size")
  private Long fileSize;

  @Column(name = "file_path")
  private String filePath;

  @Column(name = "access_url")
  private String accessUrl;
}