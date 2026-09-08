package com.example.projectmanagementbe.api.controllers;

import com.example.projectmanagementbe.api.models.dto.requests.LeaveRequest.CreateLeaveRequest;
import com.example.projectmanagementbe.api.models.dto.requests.LeaveRequest.LeaveDecisionRequest;
import com.example.projectmanagementbe.api.models.dto.requests.LeaveRequest.SearchLeaveRequest;
import com.example.projectmanagementbe.api.models.dto.requests.LeaveRequest.UpdateLeaveRequest;
import com.example.projectmanagementbe.api.models.dto.responses.LeaveResponse;
import com.example.projectmanagementbe.api.services.LeaveRequestsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/leaves")
@RequiredArgsConstructor
public class LeaveRequestController {

  private final LeaveRequestsService leaveRequestsService;
  @PostMapping
  @PreAuthorize("!hasAnyAuthority('OFM','ADMIN')")
  public ResponseEntity<LeaveResponse> create(@RequestBody CreateLeaveRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(leaveRequestsService.create(request));
  }

  @GetMapping("/me")
  public Page<LeaveResponse> getMine(Pageable pageable) {
    return leaveRequestsService.getMine(pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<LeaveResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(leaveRequestsService.findById(id));
  }

  @PutMapping("/{id}")
  @PreAuthorize("!hasAnyAuthority('OFM','ADMIN')")
  public ResponseEntity<LeaveResponse> update(@PathVariable Long id,
      @RequestBody UpdateLeaveRequest request) {
    return ResponseEntity.ok(leaveRequestsService.update(id, request));
  }

  @PostMapping("/{id}/cancel")
  @PreAuthorize("!hasAnyAuthority('OFM','ADMIN')")
  public ResponseEntity<Void> cancel(@PathVariable Long id) {
    leaveRequestsService.cancel(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{id}/approve")
  @PreAuthorize("hasAuthority('OFM')")
  public ResponseEntity<LeaveResponse> approve(@PathVariable Long id,
      @RequestBody(required = false) LeaveDecisionRequest request) {
    return ResponseEntity.ok(leaveRequestsService.approve(id, request));
  }

  @PostMapping("/{id}/reject")
  @PreAuthorize("hasAuthority('OFM')")
  public ResponseEntity<LeaveResponse> reject(@PathVariable Long id,
      @RequestBody LeaveDecisionRequest request) {
    return ResponseEntity.ok(leaveRequestsService.reject(id, request));
  }

  @PostMapping("/search")
  @PreAuthorize("hasAnyAuthority('OFM','ADMIN')")
  public Page<LeaveResponse> search(@RequestBody SearchLeaveRequest request, Pageable pageable) {
    return leaveRequestsService.search(request, pageable);
  }

  @GetMapping("/pending")
  @PreAuthorize("hasAnyAuthority('OFM','ADMIN')")
  public Page<LeaveResponse> pending(Pageable pageable) {
    return leaveRequestsService.pending(pageable);
  }
}
