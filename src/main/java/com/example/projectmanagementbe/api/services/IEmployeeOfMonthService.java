package com.example.projectmanagementbe.api.services;


import com.example.projectmanagementbe.api.models.dto.requests.employeeOfMonth.CreateEmployeeOfMonthRequest;
import com.example.projectmanagementbe.api.models.dto.requests.employeeOfMonth.SearchEmployeeOfMonthRequest;
import com.example.projectmanagementbe.api.models.dto.requests.employeeOfMonth.UpdateEmployeeOfMonthRequest;
import com.example.projectmanagementbe.api.models.dto.responses.employeeOfMonth.EmployeeOfMonthResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEmployeeOfMonthService {
    Page<EmployeeOfMonthResponse> findByParams(SearchEmployeeOfMonthRequest request, Pageable pageable);

    void create(CreateEmployeeOfMonthRequest request);

    void update(Long id, UpdateEmployeeOfMonthRequest request);

    void delete(Long id);

    EmployeeOfMonthResponse findById(Long id);
}
