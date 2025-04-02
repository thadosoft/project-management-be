package com.example.projectmanagementbe.api.repositories.inventory;

import com.example.projectmanagementbe.api.models.iventory.InventoryItem;
import com.example.projectmanagementbe.api.models.iventory.InventoryTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Long> {
  @Query("SELECT r FROM InventoryTransaction r " +
      "WHERE (:transactionType IS NULL OR :transactionType = '' OR r.transactionType = :transactionType) " +
      "AND (:receiver IS NULL OR LOWER(r.receiver) LIKE LOWER(CONCAT('%', :receiver, '%'))) " +
      "AND (:processedBy IS NULL OR LOWER(r.processedBy) LIKE LOWER(CONCAT('%', :processedBy, '%'))) ")
  Page<InventoryTransaction> searchByParams(
      @Param("transactionType") String transactionType,
      @Param("receiver") String receiver,
      @Param("processedBy") String processedBy,
      Pageable pageable
  );
}