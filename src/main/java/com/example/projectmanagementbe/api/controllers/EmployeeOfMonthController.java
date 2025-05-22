package com.example.projectmanagementbe.api.controllers;

import com.example.projectmanagementbe.api.models.dto.requests.employeeOfMonth.CreateEmployeeOfMonthRequest;
import com.example.projectmanagementbe.api.models.dto.requests.employeeOfMonth.UpdateEmployeeOfMonthRequest;
import com.example.projectmanagementbe.api.models.dto.responses.employeeOfMonth.EmployeeOfMonthResponse;
import com.example.projectmanagementbe.api.services.IEmployeeOfMonthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee-of-month")
@RequiredArgsConstructor
public class EmployeeOfMonthController {

    private final IEmployeeOfMonthService iEmployeeOfMonthService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateEmployeeOfMonthRequest request) {
        iEmployeeOfMonthService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeOfMonthResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(iEmployeeOfMonthService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdateEmployeeOfMonthRequest request) {
        iEmployeeOfMonthService.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        iEmployeeOfMonthService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
