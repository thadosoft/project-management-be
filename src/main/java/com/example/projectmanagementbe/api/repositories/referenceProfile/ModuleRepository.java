package com.example.projectmanagementbe.api.repositories.referenceProfile;

import com.example.projectmanagementbe.api.models.referenceProfile.Modules;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Modules, Long> {

}
