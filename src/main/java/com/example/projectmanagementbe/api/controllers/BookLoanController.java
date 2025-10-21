package com.example.projectmanagementbe.api.controllers;

import com.example.projectmanagementbe.api.models.dto.requests.BookLoanRequest;
import com.example.projectmanagementbe.api.models.dto.requests.CreateBookLoanRequest;
import com.example.projectmanagementbe.api.models.dto.requests.UpdateBookLoanRequest;
import com.example.projectmanagementbe.api.models.dto.responses.BookLoanResponse;
import com.example.projectmanagementbe.api.models.dto.responses.BookLoanStatsResponse;
import com.example.projectmanagementbe.api.services.BookLoanService;
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

    @PutMapping("/{id}/return")
    public ResponseEntity<Void> markAsReturned(@PathVariable Long id) {
        bookLoanService.markAsReturned(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/check-overdue")
    public ResponseEntity<String> checkOverdueLoans() {
        bookLoanService.checkAndNotifyOverdueLoans();
        return ResponseEntity.ok("Đã kiểm tra và cập nhật trạng thái quá hạn.");
    }

    @GetMapping("/stats")
    public ResponseEntity<BookLoanStatsResponse> getLoanStats() {
        return ResponseEntity.ok(bookLoanService.getLoanStatistics());
    }
}
