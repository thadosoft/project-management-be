package com.example.projectmanagementbe.api.mappers.quotation;

import com.example.projectmanagementbe.api.models.QuotationRequest;
import com.example.projectmanagementbe.api.models.dto.requests.Employee.EmployeeRequest;
import com.example.projectmanagementbe.api.models.dto.requests.quotation.CreateQuotationRequest;
import com.example.projectmanagementbe.api.models.dto.requests.quotation.UpdateQuotationRequest;
import com.example.projectmanagementbe.api.models.dto.responses.Employee.EmployeeResponse;
import com.example.projectmanagementbe.api.models.dto.responses.quotation.QuotationResponse;
import com.example.projectmanagementbe.api.models.employee.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface QuotationMapper {

  QuotationResponse mapQuotationResponse(QuotationRequest employee);

  QuotationRequest map(CreateQuotationRequest request);

  void update(UpdateQuotationRequest dto, @MappingTarget QuotationRequest entity);
}
