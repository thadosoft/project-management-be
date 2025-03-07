package com.example.projectmanagementbe.api.controllers.Employee;

import com.example.projectmanagementbe.api.models.dto.requests.Employee.EmployeeRequest;
import com.example.projectmanagementbe.api.models.dto.requests.Employee.SearchEmployeeRequest;
import com.example.projectmanagementbe.api.models.dto.responses.Employee.EmployeeResponse;
import com.example.projectmanagementbe.api.services.Employee.IEmployeeService;
import com.example.projectmanagementbe.api.services.File.ExportFileService;
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
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

  public static final String TEMPLATE_PATH = "src/main/resources/reports/templates/HDLD_TDS.jrxml";
  private final IEmployeeService employeeService;
  private final ExportFileService exportFileService;

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

  @GetMapping("/printPDF/{id}")
  public ResponseEntity<byte[]> downloadPDF(@PathVariable("id") Long id) {

    var parameters = employeeService.loadData(id);

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

  @GetMapping("/printExcel/{id}")
  public ResponseEntity<byte[]> downloadExcel(@PathVariable("id") Long id) {
    var parameters = employeeService.loadData(id);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.setContentDisposition(
        ContentDisposition.builder("attachment")
            .filename("report.pdf")
            .build()
    );
    var pdfBytes = exportFileService.exportExcelReport(TEMPLATE_PATH, parameters);
    return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
  }
}
