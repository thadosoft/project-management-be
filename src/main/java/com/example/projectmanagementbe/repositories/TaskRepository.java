package com.example.projectmanagementbe.repositories;

import com.example.projectmanagementbe.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

}
