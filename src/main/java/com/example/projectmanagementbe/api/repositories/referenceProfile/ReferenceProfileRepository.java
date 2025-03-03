package com.example.projectmanagementbe.api.repositories.referenceProfile;

import com.example.projectmanagementbe.api.models.referenceProfile.ReferenceProfile;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReferenceProfileRepository extends JpaRepository<ReferenceProfile, Long> {

  @Query("SELECT r FROM ReferenceProfile r " +
      "WHERE (:keyword IS NULL OR LOWER(r.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
      "AND (:startDate IS NULL OR :endDate IS NULL OR r.createdAt BETWEEN :startDate AND :endDate)")
  Page<ReferenceProfile> searchByNameAndDate(
      @Param("keyword") String keyword,
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate,
      Pageable pageable
  );
}