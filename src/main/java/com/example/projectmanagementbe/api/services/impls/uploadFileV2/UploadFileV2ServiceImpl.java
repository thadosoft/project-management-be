package com.example.projectmanagementbe.api.services.impls.uploadFileV2;

import com.example.projectmanagementbe.api.models.ReferenceFileV2;
import com.example.projectmanagementbe.api.repositories.ReferenceFileV2Repository;
import com.example.projectmanagementbe.api.services.uploadFile.IFileUploadV2;
import com.example.projectmanagementbe.exception.ErrorCode;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UploadFileV2ServiceImpl implements IFileUploadV2 {

  private final ReferenceFileV2Repository referenceFileV2Repository;
  private static final String UPLOAD_DIR = "uploads";

  @PostConstruct
  public void init() {
    File dir = new File(UPLOAD_DIR);
    if (!dir.exists()) {
      boolean created = dir.mkdirs();
      if (created) {
        log.info("Tạo thư mục lưu file: {}", UPLOAD_DIR);
      } else {
        log.error("Không thể tạo thư mục lưu file: {}", UPLOAD_DIR);
        throw new RuntimeException("Không thể tạo thư mục lưu file: " + UPLOAD_DIR);
      }
    }
  }


  @Override
  public void uploadFile(MultipartFile file) {
    if (file == null || file.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, ErrorCode.FILE_EMPTY.toString());
    }

    try {
      String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
      String filePath = UPLOAD_DIR + File.separator + fileName;

      File destFile = new File(filePath);
      file.transferTo(destFile);

      ReferenceFileV2 referenceFile = new ReferenceFileV2();
      referenceFile.setFileName(file.getOriginalFilename());
      referenceFile.setFileType(file.getContentType());
      referenceFile.setFileSize(file.getSize());
      referenceFile.setFilePath(filePath);

      referenceFileV2Repository.save(referenceFile);
    } catch (IOException e) {
      throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, ErrorCode.FILE_UPLOAD_FAILED.toString());
    }
  }

  @Override
  public ResponseEntity<byte[]> downloadFile(Long fileId) {

    ReferenceFileV2 file = referenceFileV2Repository.findById(fileId)
        .orElseThrow(() -> new RuntimeException("File không tìm thấy"));

    try {
      File fileToDownload = new File(file.getFilePath());
      if (!fileToDownload.exists()) {
        log.error("File không tồn tại trên hệ thống: {}", file.getFilePath());
        throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, ErrorCode.FILE_NOT_FOUND.toString());
      }

      byte[] fileData = Files.readAllBytes(fileToDownload.toPath());

      String mimeType = (file.getFileType() != null && file.getFileType().contains("/"))
          ? file.getFileType()
          : MediaType.APPLICATION_OCTET_STREAM_VALUE;

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.parseMediaType(mimeType));
      headers.setContentDisposition(ContentDisposition.attachment()
          .filename(file.getFileName()).build());

      return ResponseEntity.ok().headers(headers).body(fileData);
    } catch (IOException e) {
      log.error("Lỗi khi đọc file: {}", file.getFilePath(), e);
      throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, ErrorCode.FILE_UPLOAD_FAILED.toString());
    }
  }
}