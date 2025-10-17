package com.example.projectmanagementbe.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

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

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "borrow_date")
    private Instant borrowDate;

    @Size(max = 255)
    @Column(name = "book_owner")
    private String bookOwner;

    @Size(max = 100)
    @Column(name = "book_condition", length = 100)
    private String bookCondition;
}