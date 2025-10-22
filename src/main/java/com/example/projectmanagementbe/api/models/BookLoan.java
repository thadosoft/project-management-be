package com.example.projectmanagementbe.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "book_loans")
public class BookLoan extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "book_title")
    private String bookTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Size(max = 255)
    @Column(name = "borrower_name")
    private String borrowerName;

    @Column(name = "borrower_id")
    private Long borrowerId;

    @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss")
    private LocalDateTime borrowDate;

    @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss")
    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private LoanStatus status;

    @Size(max = 255)
    @Column(name = "approver_name")
    private String approverName;

    @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss")
    private LocalDateTime approvedAt;

    @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss")
    private LocalDateTime returnedAt;

    private String bookOwner;
    private String bookCondition;
    private Boolean isAvailable;
    private String remarks;
}
