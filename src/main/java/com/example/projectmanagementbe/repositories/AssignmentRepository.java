package com.example.projectmanagementbe.repositories;

import com.example.projectmanagementbe.entities.AssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<AssignmentEntity, Long> {

}
