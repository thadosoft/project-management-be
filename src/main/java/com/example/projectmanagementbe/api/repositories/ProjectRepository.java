package com.example.projectmanagementbe.api.repositories;

import com.example.projectmanagementbe.api.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, String> {

}
