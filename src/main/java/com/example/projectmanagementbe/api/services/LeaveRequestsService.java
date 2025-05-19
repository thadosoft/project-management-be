package com.example.projectmanagementbe.api.services;

import com.example.projectmanagementbe.api.models.dto.requests.LeaveRequest.CreateLeaveRequest;
import com.example.projectmanagementbe.api.models.dto.requests.LeaveRequest.SearchLeaveRequest;
import com.example.projectmanagementbe.api.models.dto.requests.LeaveRequest.UpdateLeaveRequest;
import com.example.projectmanagementbe.api.models.dto.responses.LeaveResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LeaveRequestsService {
    Page<LeaveResponse> searchByParams(SearchLeaveRequest request, Pageable pageable);

    void create(CreateLeaveRequest request);

    void update(Long id, UpdateLeaveRequest request);

    void delete(Long id);

    LeaveResponse findById(Long id);
}
