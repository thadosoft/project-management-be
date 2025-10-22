package com.example.projectmanagementbe.api.mappers;

import com.example.projectmanagementbe.api.models.Book;
import com.example.projectmanagementbe.api.models.BookLoan;
import com.example.projectmanagementbe.api.models.dto.requests.CreateBookLoanRequest;
import com.example.projectmanagementbe.api.models.dto.requests.UpdateBookLoanRequest;
import com.example.projectmanagementbe.api.models.dto.requests.UpdateBookRequest;
import com.example.projectmanagementbe.api.models.dto.responses.BookLoanResponse;
import com.example.projectmanagementbe.api.models.dto.responses.BookResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.Duration;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface BookMapper {

    BookResponse mapBookResponse(Book request);

    void update(UpdateBookRequest dto, @MappingTarget Book entity);
}
