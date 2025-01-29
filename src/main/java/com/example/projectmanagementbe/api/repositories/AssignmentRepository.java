package com.example.projectmanagementbe.api.repositories;

import com.example.projectmanagementbe.api.models.Assignment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, String> {

  List<Assignment> findByTask_Project_Id(String projectId);

  List<Assignment> findByTask_Id(String taskId);
}
