package com.example.projectmanagementbe.api.repositories.project;

import com.example.projectmanagementbe.api.models.project.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, String> {

  List<Task> findByProject_Id(String id);
}
