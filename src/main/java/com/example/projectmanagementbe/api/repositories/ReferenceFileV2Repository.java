package com.example.projectmanagementbe.api.repositories;

import com.example.projectmanagementbe.api.models.ReferenceFileV2;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReferenceFileV2Repository extends JpaRepository<ReferenceFileV2, Long> {
    List<ReferenceFileV2> findByInventoryItemId(Long id);
}