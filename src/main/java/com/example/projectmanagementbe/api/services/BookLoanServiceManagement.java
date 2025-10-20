package com.example.projectmanagementbe.api.services;

import com.example.projectmanagementbe.api.mappers.BookLoanMapper;
import com.example.projectmanagementbe.api.models.BookLoan;
import com.example.projectmanagementbe.api.models.LoanStatus;
import com.example.projectmanagementbe.api.models.dto.requests.BookLoanRequest;
import com.example.projectmanagementbe.api.models.dto.requests.CreateBookLoanRequest;
import com.example.projectmanagementbe.api.models.dto.requests.UpdateBookLoanRequest;
import com.example.projectmanagementbe.api.models.dto.responses.BookLoanResponse;
import com.example.projectmanagementbe.api.repositories.BookLoanRepository;
import com.example.projectmanagementbe.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.projectmanagementbe.auth.utils.StringToLocalDateTime.parseDateToLocalDateTime;

@Service
@RequiredArgsConstructor
public class BookLoanServiceManagement implements BookLoanService {

    private final BookLoanMapper bookLoanMapper;
    private final BookLoanRepository bookLoanRepository;

    private static final int MAX_BOOKS_PER_USER = 3;
    private static final int LOAN_DURATION_DAYS = 15;

    @Override
    public Page<BookLoanResponse> findByParams(BookLoanRequest request, Pageable pageable) {
        LocalDateTime startDate = parseDateToLocalDateTime(request.getStartDate(), false);
        LocalDateTime endDate = parseDateToLocalDateTime(request.getEndDate(), true);
        return bookLoanRepository
                .findByParams(request.getTitle(), startDate, endDate, pageable).map(bookLoanMapper::mapBookLoanResponse);
    }

    @Override
    @Transactional
    public void create(CreateBookLoanRequest request) {
        long activeLoans = bookLoanRepository.countByBorrowerIdAndStatusIn(
                request.getBorrowerId(),
                List.of(LoanStatus.BORROWED, LoanStatus.OVERDUE)
        );
        if (activeLoans >= MAX_BOOKS_PER_USER) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorCode.BOOK_LIMIT_EXCEEDED.getMessage());
        }

        BookLoan loan = bookLoanMapper.mapCreate(request);
        bookLoanRepository.save(loan);
    }

    @Override
    public BookLoanResponse findById(Long id) {
        return bookLoanRepository.findById(id)
                .map(bookLoanMapper::mapBookLoanResponse)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.BOOK_LOAN_NOT_FOUND.getMessage())
                );
    }

    @Override
    @Transactional
    public void update(Long id, UpdateBookLoanRequest request) {
        BookLoan loan = bookLoanRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.BOOK_LOAN_NOT_FOUND.getMessage())
                );

        bookLoanRepository.save(loan);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        BookLoan loan = bookLoanRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.BOOK_LOAN_NOT_FOUND.getMessage())
                );

        if (loan.getStatus() == LoanStatus.BORROWED) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    ErrorCode.BOOK_LOAN_ACTIVE_DELETE_DENIED.getMessage()
            );
        }

        bookLoanRepository.delete(loan);
    }

    @Override
    @Transactional
    public void markAsReturned(Long id) {
        BookLoan loan = bookLoanRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.BOOK_LOAN_NOT_FOUND.getMessage())
                );

        if (loan.getStatus() == LoanStatus.RETURNED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorCode.BOOK_ALREADY_RETURNED.getMessage());
        }

        loan.setStatus(LoanStatus.RETURNED);
        loan.setReturnedAt(LocalDateTime.now());
        loan.setIsAvailable(true);

        bookLoanRepository.save(loan);
    }

    @Override
    @Transactional
    public void checkAndNotifyOverdueLoans() {
        Instant now = Instant.now();
        List<BookLoan> overdueLoans = bookLoanRepository.findByStatusAndDueDateBefore(LoanStatus.BORROWED, now);

        for (BookLoan loan : overdueLoans) {
            loan.setStatus(LoanStatus.OVERDUE);
            bookLoanRepository.save(loan);
        }
    }

}
