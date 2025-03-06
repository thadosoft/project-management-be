package com.example.projectmanagementbe.api.services.File;

import java.util.Map;

public interface ExportFileService {
  byte[] exportPdfReport(String templatePath, Map<String, Object> parameters);

  byte[] exportExcelReport(String templatePath, Map<String, Object> parameters);
}
