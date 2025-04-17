package com.example.projectmanagementbe.api.services.uploadFile;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IFileUploadV2 {

  void uploadFile(MultipartFile file);

  ResponseEntity<byte[]> downloadFile(Long fileId);
}
