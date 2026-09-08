package com.example.projectmanagementbe.api.services;

import com.example.projectmanagementbe.api.models.dto.requests.LeaveRequest.CreateLeaveRequest;
import com.example.projectmanagementbe.api.models.dto.requests.LeaveRequest.LeaveDecisionRequest;
import com.example.projectmanagementbe.api.models.dto.requests.LeaveRequest.SearchLeaveRequest;
import com.example.projectmanagementbe.api.models.dto.requests.LeaveRequest.UpdateLeaveRequest;
import com.example.projectmanagementbe.api.models.dto.responses.LeaveResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LeaveRequestsService {

  LeaveResponse create(CreateLeaveRequest request);

  LeaveResponse update(Long id, UpdateLeaveRequest request);

  void cancel(Long id);

  LeaveResponse approve(Long id, LeaveDecisionRequest request);

  LeaveResponse reject(Long id, LeaveDecisionRequest request);

  LeaveResponse findById(Long id);

  Page<LeaveResponse> getMine(Pageable pageable);

  Page<LeaveResponse> search(SearchLeaveRequest request, Pageable pageable);

  Page<LeaveResponse> pending(Pageable pageable);
}
