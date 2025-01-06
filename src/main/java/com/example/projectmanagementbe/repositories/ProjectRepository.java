package com.example.projectmanagementbe.repositories;

import com.example.projectmanagementbe.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<ProjectEntity, String> {

}
