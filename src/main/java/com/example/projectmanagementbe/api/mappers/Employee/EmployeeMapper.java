package com.example.projectmanagementbe.api.mappers.Employee;

import com.example.projectmanagementbe.api.models.dto.requests.Employee.EmployeeRequest;
import com.example.projectmanagementbe.api.models.dto.responses.Employee.EmployeeResponse;
import com.example.projectmanagementbe.api.models.employee.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

  EmployeeResponse mapEmployeeResponse(Employee employee);

  Employee map(EmployeeRequest request);

  void update(EmployeeRequest dto, @MappingTarget Employee entity);

}
