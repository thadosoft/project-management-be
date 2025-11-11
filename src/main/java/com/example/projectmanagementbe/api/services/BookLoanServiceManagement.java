package com.example.projectmanagementbe.api.services;

import com.example.projectmanagementbe.api.mappers.BookLoanMapper;
import com.example.projectmanagementbe.api.models.Book;
import com.example.projectmanagementbe.api.models.BookLoan;
import com.example.projectmanagementbe.api.models.LoanStatus;
import com.example.projectmanagementbe.api.models.dto.requests.BookLoanRequest;
import com.example.projectmanagementbe.api.models.dto.requests.CreateBookLoanRequest;
import com.example.projectmanagementbe.api.models.dto.requests.UpdateBookLoanRequest;
import com.example.projectmanagementbe.api.models.dto.responses.BookLoanResponse;
import com.example.projectmanagementbe.api.models.dto.responses.BookLoanStatsResponse;
import com.example.projectmanagementbe.api.repositories.BookLoanRepository;
import com.example.projectmanagementbe.api.repositories.BookRepository;
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
    private final BookRepository bookRepository;

    private static final int MAX_BOOKS_PER_USER = 3;
    private static final int LOAN_DURATION_DAYS = 14;

    @Override
    public Page<BookLoanResponse> findByParams(BookLoanRequest request, Pageable pageable) {
        LocalDateTime startDate = parseDateToLocalDateTime(request.getStartDate(), false);
        LocalDateTime endDate = parseDateToLocalDateTime(request.getEndDate(), true);
        return bookLoanRepository
                .findByParams(request.getTitle(), startDate, endDate, pageable).map(bookLoanMapper::mapBookLoanResponse);
    }

    @Override
    @Transactional
    public BookLoanResponse create(CreateBookLoanRequest request) {
        long activeLoans = bookLoanRepository.countByBorrowerIdAndStatusIn(
                request.getBorrowerId(),
                List.of(LoanStatus.BORROWED, LoanStatus.OVERDUE)
        );
        if (activeLoans >= MAX_BOOKS_PER_USER) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorCode.BOOK_LIMIT_EXCEEDED.getMessage());
        }

        // Lấy thông tin sách
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorCode.BOOK_NOT_FOUND.getMessage()));

        // Kiểm tra còn sách không
        Integer availableQty = book.getQuantity_available() != null ? book.getQuantity_available() : 0;
        if (availableQty <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorCode.BOOK_OUT_OF_STOCK.getMessage());
        }

        // Giảm số lượng sách
        book.setQuantity_available(availableQty - 1);
        bookRepository.save(book);

        // Tạo record loan
        BookLoan loan = new BookLoan();
        loan.setBook(book);
        loan.setBookTitle(book.getTitle());
        loan.setApproverName(request.getApproverName()); // <-- Gán tác giả vào approver_name
        loan.setBorrowerName(request.getBorrowerName());
        loan.setBorrowerId(request.getBorrowerId());
        loan.setStatus(LoanStatus.BORROWED);
        loan.setBorrowDate(LocalDateTime.now());
        loan.setApprovedAt(LocalDateTime.now());
        loan.setBookOwner(book.getPublisher());
        loan.setBookCondition(request.getBookCondition());
        loan.setRemarks(request.getRemarks());
        loan.setDueDate(LocalDateTime.now().plusDays(14));
        bookLoanRepository.save(loan);

        // ✅ Trả về thông tin đầy đủ cho FE
        return bookLoanMapper.mapBookLoanResponse(loan);
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

        bookLoanMapper.update(request, loan);

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
        // Tìm loan theo ID
        BookLoan loan = bookLoanRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, ErrorCode.BOOK_LOAN_NOT_FOUND.getMessage()
                ));

        // Nếu đã trả rồi thì không cho trả lại nữa
        if (loan.getStatus() == LoanStatus.RETURNED) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ErrorCode.BOOK_ALREADY_RETURNED.getMessage()
            );
        }

        // Đổi trạng thái sang RETURNED
        loan.setStatus(LoanStatus.RETURNED);
        loan.setReturnedAt(LocalDateTime.now());
        loan.setIsAvailable(true);

        // Tìm book tương ứng trong DB (dựa theo title)
        Book book = bookRepository.findByTitle(loan.getBookTitle())
                .orElse(null);

        if (book != null) {
            // Tăng số lượng available lên 1
            int available = book.getQuantity_available() != null ? book.getQuantity_available() : 0;
            book.setQuantity_available(available + 1);
            bookRepository.save(book);
        } else {
            // Nếu không tìm thấy book tương ứng → ghi log hoặc bỏ qua
            System.out.println("⚠ Không tìm thấy sách: " + loan.getBookTitle());
        }

        // Lưu thay đổi loan
        bookLoanRepository.save(loan);
    }

//    @Override
//    @Transactional
//    public void checkAndNotifyOverdueLoans() {
//        Instant now = Instant.now();
//        List<BookLoan> overdueLoans = bookLoanRepository.findByStatusAndDueDateBefore(LoanStatus.BORROWED, now);
//
//        for (BookLoan loan : overdueLoans) {
//            loan.setStatus(LoanStatus.OVERDUE);
//            bookLoanRepository.save(loan);
//        }
//    }

    @Override
    @Transactional
    public void checkAndNotifyOverdueLoans() {
        List<BookLoan> activeLoans = bookLoanRepository.findByStatus(LoanStatus.BORROWED);
        LocalDateTime now = LocalDateTime.now();
        int updatedCount = 0; // 👈 Thêm biến đếm số phiếu thực sự được cập nhật

        for (BookLoan loan : activeLoans) {
            if (loan.getApprovedAt() != null && loan.getDueDate() != null) {
                if (now.isAfter(loan.getDueDate())) {
                    loan.setStatus(LoanStatus.OVERDUE);
                    bookLoanRepository.save(loan);
                    updatedCount++; // 👈 Chỉ tăng khi có thay đổi
                } else {
                    long daysRemaining = java.time.Duration.between(now, loan.getDueDate()).toDays();
                    System.out.println("Loan " + loan.getId() + " còn " + daysRemaining + " ngày trước khi hết hạn.");
                }
            }
        }

        System.out.println("✅ Đã kiểm tra và cập nhật các phiếu quá hạn (" + updatedCount + " phiếu).");
    }

    @Override
    public BookLoanStatsResponse getLoanStatistics() {
        long borrowedCount = bookLoanRepository.countByStatus(LoanStatus.BORROWED);
        long overdueCount = bookLoanRepository.countByStatus(LoanStatus.OVERDUE);
        long returnedCount = bookLoanRepository.countByStatus(LoanStatus.RETURNED);

        return new BookLoanStatsResponse(borrowedCount, overdueCount, returnedCount);
    }

}
