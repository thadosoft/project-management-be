package com.example.projectmanagementbe.api.repositories.inventory;

import com.example.projectmanagementbe.api.models.iventory.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

}