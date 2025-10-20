package com.example.projectmanagementbe.api.models.dto.requests;

import com.example.projectmanagementbe.api.models.LoanStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateBookLoanRequest {

    private String bookTitle;

    private String borrowerName;

    private Long borrowerId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime borrowDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dueDate;

    private LoanStatus status;

    private String approverName;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime approvedAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime returnedAt;

    private String bookOwner;

    private String bookCondition;

    private Boolean isAvailable;

    private String remarks;
}
