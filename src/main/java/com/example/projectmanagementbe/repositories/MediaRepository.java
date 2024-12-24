package com.example.projectmanagementbe.repositories;

import com.example.projectmanagementbe.entities.MediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<MediaEntity, Long> {

}
