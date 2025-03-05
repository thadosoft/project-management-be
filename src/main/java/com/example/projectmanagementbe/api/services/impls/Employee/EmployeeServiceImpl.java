package com.example.projectmanagementbe.api.services.impls.Employee;

import com.example.projectmanagementbe.api.mappers.Employee.EmployeeMapper;
import com.example.projectmanagementbe.api.models.dto.requests.Employee.EmployeeRequest;
import com.example.projectmanagementbe.api.models.dto.requests.Employee.SearchEmployeeRequest;
import com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.Search.SearchReferenceProfileRequest;
import com.example.projectmanagementbe.api.models.dto.responses.Employee.EmployeeResponse;
import com.example.projectmanagementbe.api.models.dto.responses.referenceProfile.ReferenceProfileResponse;
import com.example.projectmanagementbe.api.models.employee.Employee;
import com.example.projectmanagementbe.api.repositories.Employee.EmployeeRepository;
import com.example.projectmanagementbe.api.services.Employee.IEmployeeService;
import com.example.projectmanagementbe.exception.ApiRequestException;
import com.example.projectmanagementbe.exception.ErrorCode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {

  private final EmployeeRepository employeeRepository;
  private final EmployeeMapper mapper;

  @Override
  public Page<EmployeeResponse> findAll(Pageable pageable) {
    Pageable request = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt"));

    Page<Employee> referenceProfiles = employeeRepository.findAll(request);

    List<EmployeeResponse> bearingResponses = referenceProfiles.getContent().stream().map(mapper::mapEmployeeResponse).collect(Collectors.toList());

    return new PageImpl<>(bearingResponses, request, referenceProfiles.getTotalElements());
  }

  @Override
  public Page<EmployeeResponse> searchByParams(SearchEmployeeRequest searchReferenceProfileRequest, Pageable pageable) {
    return employeeRepository
        .searchByNameAndDate(searchReferenceProfileRequest.getFullName(), searchReferenceProfileRequest.getCareer(), pageable).map(mapper::mapEmployeeResponse);
  }

  @Override
  public void create(EmployeeRequest request) {
    Employee employee = mapper.map(request);
    employeeRepository.save(employee);
  }

  @Override
  public void update(Long id, EmployeeRequest request) {
    Employee module = employeeRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.EMPLOYEE_NOT_FOUND.toString()));

    mapper.update(request, module);

    employeeRepository.save(module);
  }

  @Override
  public void delete(Long id) {
    try {
      employeeRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.EMPLOYEE_NOT_FOUND.toString());
    }
  }

  @Override
  public EmployeeResponse findById(Long id) {
    return mapper.mapEmployeeResponse(employeeRepository.findById(id).orElseThrow(() -> new ApiRequestException(ErrorCode.EMPLOYEE_NOT_FOUND)));
  }

  @Override
  public Map<String, Object> loadData(Long id) {
    Optional<Employee> employmentContract = employeeRepository.findById(id);
    if (employmentContract.isPresent()) {
      var parameters = new HashMap<String, Object>();
      return parameters;
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.CONTRACT_NOT_FOUND.toString());
    }
  }
}
