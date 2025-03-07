package com.example.projectmanagementbe.api.controllers.quotation;

import com.example.projectmanagementbe.api.services.File.ExportFileService;
import com.example.projectmanagementbe.api.services.quotation.IQuotationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/quotations")
@RequiredArgsConstructor
public class QuotationRequestController {

  public static final String TEMPLATE_PATH = "src/main/resources/reports/templates/BOM.jrxml";
  private final ExportFileService exportFileService;
  private final IQuotationRequestService iQuotationRequestService;

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
