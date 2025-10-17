package com.example.projectmanagementbe.api.controllers;

import com.example.projectmanagementbe.api.models.dto.requests.BookLoanRequest;
import com.example.projectmanagementbe.api.models.dto.requests.CreateBookLoanRequest;
import com.example.projectmanagementbe.api.models.dto.requests.UpdateBookLoanRequest;
import com.example.projectmanagementbe.api.models.dto.requests.employeeOfMonth.CreateEmployeeOfMonthRequest;
import com.example.projectmanagementbe.api.models.dto.requests.employeeOfMonth.SearchEmployeeOfMonthRequest;
import com.example.projectmanagementbe.api.models.dto.requests.employeeOfMonth.UpdateEmployeeOfMonthRequest;
import com.example.projectmanagementbe.api.models.dto.responses.BookLoanResponse;
import com.example.projectmanagementbe.api.models.dto.responses.employeeOfMonth.BookLoanResponse;
import com.example.projectmanagementbe.api.services.BookLoanService;
import com.example.projectmanagementbe.api.services.bookLoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/book-loans")
@RequiredArgsConstructor
public class BookLoanController {

    private final BookLoanService bookLoanService;

    @PostMapping("/search")
    public Page<BookLoanResponse> search(@RequestBody BookLoanRequest request, Pageable pageable) {
        return bookLoanService.findByParams(request, pageable);
    }


    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateBookLoanRequest request) {
        bookLoanService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookLoanResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(bookLoanService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdateBookLoanRequest request) {
        bookLoanService.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookLoanService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
