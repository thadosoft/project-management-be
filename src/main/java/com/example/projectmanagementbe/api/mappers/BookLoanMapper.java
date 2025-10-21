package com.example.projectmanagementbe.api.mappers;

import com.example.projectmanagementbe.api.models.Book;
import com.example.projectmanagementbe.api.models.BookLoan;
import com.example.projectmanagementbe.api.models.dto.requests.CreateBookLoanRequest;
import com.example.projectmanagementbe.api.models.dto.requests.UpdateBookLoanRequest;
import com.example.projectmanagementbe.api.models.dto.responses.BookLoanResponse;
import org.mapstruct.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface BookLoanMapper {

  BookLoanResponse mapBookLoanResponse(BookLoan request);

  @Mapping(target = "book", expression = "java(mapBookFromId(request.getBookId()))")
  @Mapping(target = "borrowDate", expression = "java(request.getBorrowDate() != null ? request.getBorrowDate() : LocalDateTime.now())")
  @Mapping(target = "status", expression = "java(request.getStatus() != null ? request.getStatus() : com.example.projectmanagementbe.api.models.LoanStatus.BORROWED)")
  @AfterMapping
  default void calculateDuration(@MappingTarget BookLoanResponse response, BookLoan entity) {
    if (entity.getApprovedAt() != null && entity.getDueDate() != null) {
      response.setBorrowDurationDays(
              Duration.between(entity.getApprovedAt(), entity.getDueDate()).toDays()
      );
    }
  }

  BookLoan mapCreate(CreateBookLoanRequest request);

  void update(UpdateBookLoanRequest dto, @MappingTarget BookLoan entity);

  // helper method để map bookId → Book entity (chỉ cần id)
  default Book mapBookFromId(Long id) {
    if (id == null) return null;
    Book book = new Book();
    book.setId(id);
    return book;
  }
}
