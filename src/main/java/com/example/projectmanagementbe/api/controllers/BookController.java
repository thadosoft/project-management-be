package com.example.projectmanagementbe.api.controllers;

import com.example.projectmanagementbe.api.models.dto.requests.CreateBookRequest;
import com.example.projectmanagementbe.api.models.dto.responses.BookResponse;
import com.example.projectmanagementbe.api.services.BookServiceManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookServiceManagement bookService;

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody CreateBookRequest request) {
        BookResponse response = bookService.create(request);
        return ResponseEntity.ok(response);
    }
}
