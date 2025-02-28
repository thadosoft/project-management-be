package com.example.projectmanagementbe.api.models.iventory;

import com.example.projectmanagementbe.api.models.Auditable;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "inventory_item")
public class InventoryItem  extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Size(max = 255)
  @NotNull
  @Column(name = "name", nullable = false)
  private String name;

  @Size(max = 255)
  @NotNull
  @Column(name = "sku", nullable = false)
  private String sku;

  @ManyToOne
  @JoinColumn(name = "inventory_category_id", nullable = false)
  @JsonBackReference
  private InventoryCategory inventoryCategory;

  @Size(max = 50)
  @NotNull
  @Column(name = "unit", nullable = false, length = 50)
  private String unit;

  @Column(name = "quantity_in_stock")
  private Integer quantityInStock;

  @Column(name = "reorder_level")
  private Integer reorderLevel;

  @Size(max = 255)
  @Column(name = "location")
  private String location;

  @Column(name = "purchase_price", precision = 10, scale = 2)
  private BigDecimal purchasePrice;

  @Column(name = "selling_price", precision = 10, scale = 2)
  private BigDecimal sellingPrice;

  @OneToMany(mappedBy = "item")
  private List<InventoryTransaction> inventoryTransactions;
}
