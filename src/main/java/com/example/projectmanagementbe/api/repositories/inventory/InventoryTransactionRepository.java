package com.example.projectmanagementbe.api.repositories.inventory;

import com.example.projectmanagementbe.api.models.iventory.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Long> {

}