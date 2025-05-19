package com.example.projectmanagementbe.api.models.dto.requests.LeaveRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchLeaveRequest {
    private String startDate;

    private String endDate;
}
