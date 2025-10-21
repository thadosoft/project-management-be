package com.example.projectmanagementbe.api.models.dto.responses;

import com.example.projectmanagementbe.api.models.LoanStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookLoanResponse {

    private Long id;

    private String bookTitle;

    private String borrowerName;

    private Long borrowerId;

    private LocalDateTime borrowDate;

    private LocalDateTime dueDate;

    private LoanStatus status;

    private String approverName;

    private LocalDateTime approvedAt;

    private LocalDateTime returnedAt;

    private String bookOwner;

    private String bookCondition;

    private Boolean isAvailable;

    private String remarks;

    private Long borrowDurationDays;

    private LocalDateTime createdAt;
}
