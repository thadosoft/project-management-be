package com.example.projectmanagementbe.repositories;

import com.example.projectmanagementbe.entities.TaskEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, String> {

  List<TaskEntity> findByProject_Id(String id);
}
