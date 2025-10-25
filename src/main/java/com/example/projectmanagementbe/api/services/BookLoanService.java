package com.example.projectmanagementbe.api.services;

import com.example.projectmanagementbe.api.models.dto.requests.BookLoanRequest;
import com.example.projectmanagementbe.api.models.dto.requests.CreateBookLoanRequest;
import com.example.projectmanagementbe.api.models.dto.requests.UpdateBookLoanRequest;
import com.example.projectmanagementbe.api.models.dto.responses.BookLoanResponse;
import com.example.projectmanagementbe.api.models.dto.responses.BookLoanStatsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookLoanService {

    Page<BookLoanResponse> findByParams(BookLoanRequest request, Pageable pageable);

    BookLoanResponse create(CreateBookLoanRequest request);

    BookLoanResponse findById(Long id);

    void update(Long id, UpdateBookLoanRequest request);

    void delete(Long id);

    void markAsReturned(Long id);

    void checkAndNotifyOverdueLoans();

    BookLoanStatsResponse getLoanStatistics();

}
