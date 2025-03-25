package com.example.projectmanagementbe.api.services.Employee;

import com.example.projectmanagementbe.api.models.dto.requests.Employee.EmployeeRequest;
import com.example.projectmanagementbe.api.models.dto.requests.Employee.SearchEmployeeRequest;
import com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.Search.SearchReferenceProfileRequest;
import com.example.projectmanagementbe.api.models.dto.responses.Employee.EmployeeResponse;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEmployeeService {

  List<EmployeeResponse> findAll();

  Page<EmployeeResponse> searchByParams(SearchEmployeeRequest searchReferenceProfileRequest, Pageable pageable);

  void create(EmployeeRequest request);

  void update(Long id, EmployeeRequest request);

  void delete(Long id);

  EmployeeResponse findById(Long id);

  Map<String, Object> loadData(Long id);
}
