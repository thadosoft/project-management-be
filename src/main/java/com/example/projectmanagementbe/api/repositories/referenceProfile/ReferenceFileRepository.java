package com.example.projectmanagementbe.api.repositories.referenceProfile;

import com.example.projectmanagementbe.api.models.referenceProfile.ReferenceFile;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReferenceFileRepository extends JpaRepository<ReferenceFile, Long> {
  List<ReferenceFile> findByReferenceProfileId(Long id);
}