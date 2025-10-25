package com.example.projectmanagementbe.api.services;

import com.example.projectmanagementbe.api.mappers.BookMapper;
import com.example.projectmanagementbe.api.models.Book;
import com.example.projectmanagementbe.api.models.BookLoan;
import com.example.projectmanagementbe.api.models.LoanStatus;
import com.example.projectmanagementbe.api.models.dto.requests.BookRequest;
import com.example.projectmanagementbe.api.models.dto.requests.CreateBookRequest;
import com.example.projectmanagementbe.api.models.dto.requests.UpdateBookRequest;
import com.example.projectmanagementbe.api.models.dto.responses.BookResponse;
import com.example.projectmanagementbe.api.repositories.BookRepository;
import com.example.projectmanagementbe.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static com.example.projectmanagementbe.auth.utils.StringToLocalDateTime.parseDateToLocalDateTime;

@Service
@RequiredArgsConstructor
public class BookServiceManagement implements BookService{

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public Page<BookResponse> findByParams(BookRequest request, Pageable pageable) {
        return bookRepository
                .findByParams(request.getTitle(), pageable).map(bookMapper::mapBookResponse);

    }

    @Override
    public BookResponse create(CreateBookRequest request) {
        if (bookRepository.existsByTitle(request.getTitle())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorCode.BOOK_ALREADY_EXISTS.getMessage());
        }

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setCategory(request.getCategory());
        book.setPublisher(request.getPublisher());
        book.setPublicationYear(request.getPublicationYear());
        book.setQuantity_total(request.getQuantity_total());
        book.setLocation(request.getLocation());
// ✅ Khi tạo mới, nếu quantity_available chưa có → gán bằng quantity_total
//        if (request.getQuantity_available() == null) {
//            book.setQuantity_available(request.getQuantity_total());
//        } else {
//            book.setQuantity_available(request.getQuantity_available());
//        }
        Integer total = request.getQuantity_total() != null ? request.getQuantity_total() : 0;
        Integer available = request.getQuantity_available() != null ? request.getQuantity_available() : total;

        book.setQuantity_total(total);
        book.setQuantity_available(available);

        bookRepository.save(book);

        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthor(book.getAuthor());
        response.setCategory(book.getCategory());
        response.setPublisher(book.getPublisher());
        response.setPublicationYear(book.getPublicationYear());
        response.setQuantity_total(book.getQuantity_total());
        response.setQuantity_available(book.getQuantity_available());
        response.setLocation(book.getLocation());
        return response;
    }

    @Override
    public BookResponse findById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::mapBookResponse)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.BOOK_NOT_FOUND.getMessage())
                );
    }

    @Override
    public void update(Long id, UpdateBookRequest request) {
        Book loan = bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.BOOK_NOT_FOUND.getMessage())
                );

        bookMapper.update(request, loan);

        bookRepository.save(loan);
    }

    @Override
    public void delete(Long id) {
        Book loan = bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.BOOK_NOT_FOUND.getMessage())
                );

        bookRepository.delete(loan);
    }
}
