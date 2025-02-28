package com.example.projectmanagementbe.api.repositories.inventory;

import com.example.projectmanagementbe.api.models.iventory.InventoryCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryCategoryRepository extends JpaRepository<InventoryCategory, Long> {

}