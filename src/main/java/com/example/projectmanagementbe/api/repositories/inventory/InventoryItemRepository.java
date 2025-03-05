package com.example.projectmanagementbe.api.repositories.inventory;

import com.example.projectmanagementbe.api.models.iventory.InventoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
  @Query("SELECT r FROM InventoryItem r " +
      "WHERE (:name IS NULL OR LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
      "AND (:sku IS NULL OR LOWER(r.sku) LIKE LOWER(CONCAT('%', :sku, '%'))) ")
  Page<InventoryItem> searchByNameAndSku(
      @Param("name") String name,
      @Param("sku") String sku,
      Pageable pageable
  );
}