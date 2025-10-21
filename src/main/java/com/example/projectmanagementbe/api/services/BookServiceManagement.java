package com.example.projectmanagementbe.api.services;

import com.example.projectmanagementbe.api.models.Book;
import com.example.projectmanagementbe.api.models.dto.requests.CreateBookRequest;
import com.example.projectmanagementbe.api.models.dto.responses.BookResponse;
import com.example.projectmanagementbe.api.repositories.BookRepository;
import com.example.projectmanagementbe.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class BookServiceManagement {

    private final BookRepository bookRepository;

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
// ✅ Khi tạo mới, nếu quantity_available chưa có → gán bằng quantity_total
        if (request.getQuantity_available() == null) {
            book.setQuantity_available(request.getQuantity_total());
        } else {
            book.setQuantity_available(request.getQuantity_available());
        }
        bookRepository.save(book);

        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthor(book.getAuthor());
        response.setCategory(book.getCategory());
        response.setPublisher(book.getPublisher());
        response.setPublicationYear(book.getPublicationYear());
        response.setQuantity(book.getQuantity_total());
        response.setAvailable(book.getQuantity_available() != null && book.getQuantity_available() > 0);
        return response;
    }
}
