package com.example.projectmanagementbe.api.repositories;

import com.example.projectmanagementbe.api.models.lunch.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    @Query("""
        SELECT f FROM Food f
        WHERE f.isAvailable = true
            AND (:name IS NULL OR :name = '' OR LOWER(f.name) LIKE LOWER(CONCAT('%', :name, '%')))
            AND (:category IS NULL OR :category = '' OR f.category = :category)
    """)
    Page<Food> findByParams(
            @Param("name") String name,
            @Param("category") String category,
            Pageable pageable
    );
}