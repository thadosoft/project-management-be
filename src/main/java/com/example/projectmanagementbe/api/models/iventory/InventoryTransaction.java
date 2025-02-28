package com.example.projectmanagementbe.api.models.iventory;

import com.example.projectmanagementbe.api.models.Auditable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "inventory_transaction")
public class InventoryTransaction extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "item_id", nullable = false)
  @JsonBackReference
  private InventoryItem item;

  @Size(max = 50)
  @NotNull
  @Column(name = "transaction_type", nullable = false, length = 50)
  private String transactionType;

  @NotNull
  @Column(name = "quantity", nullable = false)
  private Integer quantity;

  @Column(name = "transaction_date")
  private Date transactionDate;

  @Column(name = "reason")
  private String reason;

  @Size(max = 255)
  @NotNull
  @Column(name = "processed_by", nullable = false)
  private String processedBy;

  @Size(max = 255)
  @Column(name = "receiver")
  private String receiver;
}
