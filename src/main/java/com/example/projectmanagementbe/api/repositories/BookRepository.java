package com.example.projectmanagementbe.api.repositories;

import com.example.projectmanagementbe.api.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitle(String title);

    boolean existsByTitle(String title);

    @Query("SELECT r FROM Book r " +
            "WHERE (:title IS NULL OR LOWER(r.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:author IS NULL OR LOWER(r.author) LIKE LOWER(CONCAT('%', :author, '%'))) " +
            "AND (:publisher IS NULL OR LOWER(r.publisher) LIKE LOWER(CONCAT('%', :publisher, '%'))) " +
            "ORDER BY r.id DESC")
    Page<Book> findByParams(
            @Param("title") String title,
            @Param("author") String author,
            @Param("publisher") String publisher,
            Pageable pageable
    );

}
