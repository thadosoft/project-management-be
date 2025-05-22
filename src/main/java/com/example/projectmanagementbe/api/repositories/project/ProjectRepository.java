package com.example.projectmanagementbe.api.repositories.project;

import com.example.projectmanagementbe.api.models.project.Project;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Project, String> {

  Optional<Project> findByName(String name);

  @Query(value = """
    SELECT 
        p.name AS projectName,
        (CAST(SUM(CASE WHEN a.status_type = 'DONE' THEN 1 ELSE 0 END) AS DECIMAL(5,2)) / 
         NULLIF(COUNT(a.id), 0)) * 100 AS progressPercentage,
        CASE 
            WHEN SUM(CASE WHEN a.status_type != 'DONE' THEN 1 ELSE 0 END) > 0 THEN 'IN_PROGRESS'
            ELSE 'DONE'
        END AS status
    FROM projects p
    LEFT JOIN tasks t ON p.id = t.project_id
    LEFT JOIN assignments a ON t.id = a.task_id
    GROUP BY p.id, p.name
    ORDER BY progressPercentage DESC
    LIMIT 5
    """, nativeQuery = true)
  List<Object[]> fetchProjectProgress();


}
