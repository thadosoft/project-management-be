package com.example.projectmanagementbe.api.repositories;

import com.example.projectmanagementbe.api.models.Event;
import com.example.projectmanagementbe.api.models.EventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    @Query("""
        SELECT e FROM Event e
        WHERE (:title IS NULL OR LOWER(e.title) LIKE LOWER(CONCAT('%', :title, '%')))
          AND ((:startDate IS NULL OR :endDate IS NULL) OR e.startDate BETWEEN :startDate AND :endDate)
          AND (:type IS NULL OR e.type = :type)
        ORDER BY e.startDate DESC
        """)
    Page<Event> findByParams(
            @Param("title") String title,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("type") EventType type,
            Pageable pageable
    );

    // Lấy tất cả event theo type
    List<Event> findByType(String type);

    // Đếm tổng số event theo type (dùng cho thống kê)
    long countByType(String type);
}
