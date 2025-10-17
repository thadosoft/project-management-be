package com.example.projectmanagementbe.api.mappers;

import com.example.projectmanagementbe.api.models.BookLoan;
import com.example.projectmanagementbe.api.models.EmployeeOfMonth;
import com.example.projectmanagementbe.api.models.dto.requests.employeeOfMonth.CreateEmployeeOfMonthRequest;
import com.example.projectmanagementbe.api.models.dto.requests.employeeOfMonth.UpdateEmployeeOfMonthRequest;
import com.example.projectmanagementbe.api.models.dto.responses.BookLoanResponse;
import com.example.projectmanagementbe.api.models.dto.responses.employeeOfMonth.EmployeeOfMonthResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookLoanMapper {

  BookLoanResponse mapBookLoanResponse(BookLoan employee);

  @Mapping(source = "employeeId", target = "employee.id")
  EmployeeOfMonth mapEmployeeOfMonth(CreateEmployeeOfMonthRequest request);

  void update(UpdateEmployeeOfMonthRequest dto, @MappingTarget EmployeeOfMonth entity);
}
