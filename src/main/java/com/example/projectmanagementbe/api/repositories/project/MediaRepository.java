package com.example.projectmanagementbe.api.repositories.project;

import com.example.projectmanagementbe.api.models.project.Media;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, String> {

  boolean existsByAssignment_IdAndName(String assignmentId, @NotBlank(message = "Media name cannot be blank") String name);

  Media findByName(@NotBlank(message = "Media name cannot be blank") String name);
}
