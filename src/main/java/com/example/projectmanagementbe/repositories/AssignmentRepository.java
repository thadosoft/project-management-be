package com.example.projectmanagementbe.repositories;

import com.example.projectmanagementbe.entities.AssignmentEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<AssignmentEntity, Long> {
  List<AssignmentEntity> findByTask_Project_Id(Long id);
}
