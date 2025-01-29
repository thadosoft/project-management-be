package com.example.projectmanagementbe.api.repositories;

import com.example.projectmanagementbe.api.models.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, String> {

}
