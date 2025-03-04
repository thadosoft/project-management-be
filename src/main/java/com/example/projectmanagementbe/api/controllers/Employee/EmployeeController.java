package com.example.projectmanagementbe.api.controllers.Employee;

import com.example.projectmanagementbe.api.models.dto.requests.Employee.EmployeeRequest;
import com.example.projectmanagementbe.api.models.dto.requests.Employee.SearchEmployeeRequest;
import com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.Search.SearchReferenceProfileRequest;
import com.example.projectmanagementbe.api.models.dto.responses.Employee.EmployeeResponse;
import com.example.projectmanagementbe.api.models.dto.responses.referenceProfile.ReferenceProfileResponse;
import com.example.projectmanagementbe.api.services.Employee.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

  private final IEmployeeService employeeService;

  @GetMapping
  public ResponseEntity<Page<EmployeeResponse>> findAll(Pageable pageable) {
    return ResponseEntity.ok(employeeService.findAll(pageable));
  }


  @PostMapping
  public ResponseEntity<Void> create(@RequestBody EmployeeRequest request) {
    employeeService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody EmployeeRequest request) {
    employeeService.update(id, request);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    employeeService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<EmployeeResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(employeeService.findById(id));
  }

  @PostMapping("/search")
  public Page<EmployeeResponse> search(@RequestBody SearchEmployeeRequest searchReferenceProfileRequest, Pageable pageable) {
    return employeeService.searchByParams(searchReferenceProfileRequest, pageable);
  }
}
