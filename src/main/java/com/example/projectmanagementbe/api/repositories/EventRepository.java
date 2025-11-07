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

    @Query(value = """
        SELECT e FROM Event e
        WHERE 
            (:title IS NULL OR :title = '' OR LOWER(e.title) LIKE LOWER(CONCAT('%', :title, '%')))
        AND (:type IS NULL OR e.type = :type)
        AND (
            (:date IS NOT NULL AND FUNCTION('DATE', e.startDate) = CAST(:date AS date))
            OR (:date IS NULL AND (
                (:month IS NOT NULL AND :year IS NOT NULL AND FUNCTION('YEAR', e.startDate) = :year AND FUNCTION('MONTH', e.startDate) = :month)
                OR (:quarter IS NOT NULL AND :year IS NOT NULL AND FUNCTION('YEAR', e.startDate) = :year
                    AND FUNCTION('MONTH', e.startDate) BETWEEN ((:quarter - 1) * 3 + 1) AND ((:quarter - 1) * 3 + 3))
                OR (:year IS NOT NULL AND :month IS NULL AND :quarter IS NULL AND FUNCTION('YEAR', e.startDate) = :year)
                OR (:date IS NULL AND :month IS NULL AND :quarter IS NULL AND :year IS NULL)
            ))
        )
    """)
    Page<Event> findByParams(
            @Param("title") String title,
            @Param("type") EventType type,
            @Param("date") String date,
            @Param("month") Integer month,
            @Param("quarter") Integer quarter,
            @Param("year") Integer year,
            Pageable pageable
    );
    // Lấy tất cả event theo type
    List<Event> findByType(String type);

    // Đếm tổng số event theo type (dùng cho thống kê)
    long countByType(String type);
}
