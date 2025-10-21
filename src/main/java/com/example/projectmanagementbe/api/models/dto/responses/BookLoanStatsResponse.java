package com.example.projectmanagementbe.api.models.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookLoanStatsResponse {
    private long borrowedCount;
    private long overdueCount;
    private long returnedCount;
}
