package com.example.projectmanagementbe.api.models.dto.responses;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class BookLoanResponse {

    private Long id;

    private String bookTitle;

    private String borrowerName;

    private Instant borrowDate;

    private String bookOwner;

    private String bookCondition;

    private LocalDateTime createdAt;
}
