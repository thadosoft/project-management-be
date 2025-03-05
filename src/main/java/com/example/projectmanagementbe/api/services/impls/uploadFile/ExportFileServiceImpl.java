package com.example.projectmanagementbe.api.services.impls.uploadFile;

import com.example.projectmanagementbe.api.models.ReportTypeExport;
import com.example.projectmanagementbe.api.services.File.ExportFileService;
import com.example.projectmanagementbe.exception.ErrorCode;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Log4j2
public class ExportFileServiceImpl implements ExportFileService {

  @Override
  public byte[] exportPdfReport(String templatePath, Map<String, Object> parameters) {
    return exportReport(templatePath, parameters, ReportTypeExport.PDF);
  }

  @Override
  public byte[] exportExcelReport(String templatePath, Map<String, Object> parameters) {
    return exportReport(templatePath, parameters, ReportTypeExport.EXCEL);
  }

  private byte[] exportReport(String templatePath, Map<String, Object> parameters, ReportTypeExport reportType) {
    try {
      var jasperReport = JasperCompileManager.compileReport(templatePath);
      var jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
      var outputStream = new ByteArrayOutputStream();

      switch (reportType) {
        case PDF -> {
          var exporter = new JRPdfExporter();
          exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
          exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
          exporter.exportReport();
        }
        case EXCEL -> {
          var exporter = new JRXlsxExporter();
          exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
          exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

          var reportConfig = new SimpleXlsxReportConfiguration();
          reportConfig.setDetectCellType(true);
          reportConfig.setCollapseRowSpan(false);
          reportConfig.setWhitePageBackground(false);
          reportConfig.setRemoveEmptySpaceBetweenRows(true);

          exporter.setConfiguration(reportConfig);
          exporter.exportReport();
        }
      }
      return outputStream.toByteArray();
    } catch (JRException e) {
      log.error("Failed to export report", e);
      throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, ErrorCode.EXPORT_REPORT_FAILED.toString());
    }
  }
}
