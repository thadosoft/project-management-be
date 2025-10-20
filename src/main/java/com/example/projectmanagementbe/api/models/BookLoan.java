package com.example.projectmanagementbe.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
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

    @Size(max = 255)
    @Column(name = "borrower_name")
    private String borrowerName;

    @Column(name = "borrower_id")
    private Long borrowerId;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "borrow_date")
    private LocalDateTime borrowDate;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private LoanStatus status;

    @Size(max = 255)
    @Column(name = "approver_name")
    private String approverName;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "returned_at")
    private LocalDateTime returnedAt;

    @Size(max = 255)
    @Column(name = "book_owner")
    private String bookOwner;

    @Size(max = 100)
    @Column(name = "book_condition", length = 100)
    private String bookCondition;

    @Column(name = "is_available")
    @ColumnDefault("true")
    private Boolean isAvailable;

    @Size(max = 500)
    @Column(name = "remarks", length = 500)
    private String remarks;
}