package com.example.projectmanagementbe.api.models.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookLoanRequest {

    private String title;

    private String startDate;

    private String endDate;
}
