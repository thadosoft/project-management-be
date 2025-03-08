package com.example.projectmanagementbe.api.controllers.quotation;

import com.example.projectmanagementbe.api.models.dto.requests.Employee.EmployeeRequest;
import com.example.projectmanagementbe.api.models.dto.requests.Employee.SearchEmployeeRequest;
import com.example.projectmanagementbe.api.models.dto.requests.quotation.CreateQuotationRequest;
import com.example.projectmanagementbe.api.models.dto.requests.quotation.SearchQuotationRequest;
import com.example.projectmanagementbe.api.models.dto.requests.quotation.UpdateQuotationRequest;
import com.example.projectmanagementbe.api.models.dto.responses.Employee.EmployeeResponse;
import com.example.projectmanagementbe.api.models.dto.responses.quotation.QuotationResponse;
import com.example.projectmanagementbe.api.services.File.ExportFileService;
import com.example.projectmanagementbe.api.services.quotation.IQuotationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping("/api/v1/quotations")
@RequiredArgsConstructor
public class QuotationRequestController {

  public static final String TEMPLATE_PATH = "src/main/resources/reports/templates/BOM.jrxml";
  private final ExportFileService exportFileService;
  private final IQuotationRequestService iQuotationRequestService;

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody CreateQuotationRequest request) {
    iQuotationRequestService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdateQuotationRequest request) {
    iQuotationRequestService.update(id, request);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    iQuotationRequestService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/search")
  public Page<QuotationResponse> search(@RequestBody SearchQuotationRequest request, Pageable pageable) {
    return iQuotationRequestService.searchByParams(request, pageable);
  }

  @GetMapping("/printPDF/{id}")
  public ResponseEntity<byte[]> downloadPDF(@PathVariable("id") Long id) {

    var parameters = iQuotationRequestService.loadData(id);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.setContentDisposition(
        ContentDisposition.builder("attachment")
            .filename("report.pdf")
            .build()
    );

    var pdfBytes = exportFileService.exportPdfReport(TEMPLATE_PATH, parameters);

    return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
  }
}
