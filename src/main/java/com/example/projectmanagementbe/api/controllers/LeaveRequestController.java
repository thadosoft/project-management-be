package com.example.projectmanagementbe.api.controllers;

import com.example.projectmanagementbe.api.models.dto.requests.LeaveRequest.CreateLeaveRequest;
import com.example.projectmanagementbe.api.models.dto.requests.LeaveRequest.SearchLeaveRequest;
import com.example.projectmanagementbe.api.models.dto.requests.LeaveRequest.UpdateLeaveRequest;
import com.example.projectmanagementbe.api.models.dto.responses.LeaveResponse;
import com.example.projectmanagementbe.api.services.LeaveRequestsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/leaves")
@RequiredArgsConstructor
public class LeaveRequestController {

  private final LeaveRequestsService leaveRequestsService;

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody CreateLeaveRequest request) {
    leaveRequestsService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<LeaveResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(leaveRequestsService.findById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdateLeaveRequest request) {
    leaveRequestsService.update(id, request);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    leaveRequestsService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/search")
  public Page<LeaveResponse> search(@RequestBody SearchLeaveRequest request, Pageable pageable) {
    return leaveRequestsService.searchByParams(request, pageable);
  }
}
