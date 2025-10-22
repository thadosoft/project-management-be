package com.example.projectmanagementbe.api.services;

import com.example.projectmanagementbe.api.models.dto.requests.*;
import com.example.projectmanagementbe.api.models.dto.responses.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    Page<BookResponse> findByParams(BookRequest request, Pageable pageable);

    BookResponse create(CreateBookRequest request);

    BookResponse findById(Long id);

    void update(Long id, UpdateBookRequest request);

    void delete(Long id);
}
