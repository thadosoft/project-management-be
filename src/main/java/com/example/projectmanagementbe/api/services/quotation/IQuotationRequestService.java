package com.example.projectmanagementbe.api.services.quotation;

import com.example.projectmanagementbe.api.models.dto.requests.Employee.EmployeeRequest;
import com.example.projectmanagementbe.api.models.dto.requests.quotation.CreateQuotationRequest;
import com.example.projectmanagementbe.api.models.dto.requests.quotation.SearchQuotationRequest;
import com.example.projectmanagementbe.api.models.dto.requests.quotation.UpdateQuotationRequest;
import com.example.projectmanagementbe.api.models.dto.responses.quotation.QuotationResponse;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IQuotationRequestService {

  Page<QuotationResponse> searchByParams(SearchQuotationRequest request, Pageable pageable);

  void create(CreateQuotationRequest request);

  void update(Long id, UpdateQuotationRequest request);

  void delete(Long id);

  QuotationResponse findById(Long id);

  Map<String, Object> loadData(Long id);
}
