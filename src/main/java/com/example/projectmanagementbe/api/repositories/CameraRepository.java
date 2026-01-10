package com.example.projectmanagementbe.api.repositories;

import com.example.projectmanagementbe.api.models.Camera;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CameraRepository extends JpaRepository<Camera, Long>, JpaSpecificationExecutor<Camera> {

    @Query("""
        SELECT c FROM Camera c
        WHERE 
            (:modelName IS NULL OR :modelName = '' 
                OR LOWER(c.modelName) LIKE LOWER(CONCAT('%', :modelName, '%')))
            AND (:type IS NULL OR :type = '' OR c.type = :type)
    """)
    Page<Camera> findByParams(
            @Param("modelName") String modelName,
            @Param("type") String type,
            Pageable pageable
    );

    // Lấy tất cả camera theo type
    List<Camera> findByType(String type);

    // Đếm số camera theo type (phục vụ thống kê)
    long countByType(String type);
}
