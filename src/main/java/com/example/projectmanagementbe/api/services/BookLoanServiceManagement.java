package com.example.projectmanagementbe.api.services;

import com.example.projectmanagementbe.api.mappers.BookLoanMapper;
import com.example.projectmanagementbe.api.models.dto.requests.BookLoanRequest;
import com.example.projectmanagementbe.api.models.dto.requests.CreateBookLoanRequest;
import com.example.projectmanagementbe.api.models.dto.requests.UpdateBookLoanRequest;
import com.example.projectmanagementbe.api.models.dto.responses.BookLoanResponse;
import com.example.projectmanagementbe.api.repositories.BookLoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.example.projectmanagementbe.auth.utils.StringToLocalDateTime.parseDateToLocalDateTime;

@Service
@RequiredArgsConstructor
public class BookLoanServiceManagement implements BookLoanService {

    private final BookLoanMapper bookLoanMapper;
    private final BookLoanRepository bookLoanRepository;

    @Override
    public Page<BookLoanResponse> findByParams(BookLoanRequest request, Pageable pageable) {
        LocalDateTime startDate = parseDateToLocalDateTime(request.getStartDate(), false);
        LocalDateTime endDate = parseDateToLocalDateTime(request.getEndDate(), true);
        return bookLoanRepository
                .findByParams(request.getTitle(), startDate, endDate, pageable).map(bookLoanMapper::mapBookLoanResponse);
    }

    @Override
    public void create(CreateBookLoanRequest request) {

    }

    @Override
    public BookLoanResponse findById(Long id) {
        return null;
    }

    @Override
    public void update(Long id, UpdateBookLoanRequest request) {

    }

    @Override
    public void delete(Long id) {

    }
}
