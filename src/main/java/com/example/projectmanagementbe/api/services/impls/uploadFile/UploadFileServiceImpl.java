package com.example.projectmanagementbe.api.services.impls.uploadFile;

import com.example.projectmanagementbe.api.models.dto.responses.referenceProfile.ReferenceFileResponse;
import com.example.projectmanagementbe.api.models.referenceProfile.ReferenceFile;
import com.example.projectmanagementbe.api.models.referenceProfile.ReferenceProfile;
import com.example.projectmanagementbe.api.repositories.referenceProfile.ReferenceFileRepository;
import com.example.projectmanagementbe.api.repositories.referenceProfile.ReferenceProfileRepository;
import com.example.projectmanagementbe.api.services.referenceProfile.IFileUpload;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
public class UploadFileServiceImpl implements IFileUpload {

  private final ReferenceFileRepository referenceFileRepository;
  private final ReferenceProfileRepository referenceProfileRepository;


  // ✅ Upload file theo referenceProfileId
  public void uploadFile(Long referenceProfileId, MultipartFile file) {
    ReferenceProfile profile = referenceProfileRepository.findById(referenceProfileId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reference Profile không tồn tại"));

    try {
      ReferenceFile referenceFile = new ReferenceFile();
      referenceFile.setReferenceProfile(profile);
      referenceFile.setFileName(file.getOriginalFilename());
      referenceFile.setFileType(file.getContentType());
      referenceFile.setFileSize(file.getSize());
      referenceFile.setFileData(file.getBytes());

      referenceFileRepository.save(referenceFile);
    } catch (IOException e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi khi lưu file", e);
    }
  }

  public List<ReferenceFileResponse> getFilesByProfile(Long referenceProfileId) {
    return referenceFileRepository.findByReferenceProfileId(referenceProfileId).stream()
        .map(UploadFileServiceImpl::toResponse)
        .toList();
  }

  private static ReferenceFileResponse toResponse(ReferenceFile file) {
    ReferenceFileResponse response = new ReferenceFileResponse();
    response.setId(file.getId());
    response.setReferenceProfileId(file.getReferenceProfile().getId());
    response.setFileName(file.getFileName());
    response.setFileType(file.getFileType());
    response.setFileSize(file.getFileSize());
    response.setFileUrl(file.getFileUrl());
    return response;
  }

  @Override
  public ResponseEntity<byte[]> downloadFile(Long fileId) {
    ReferenceFile file = referenceFileRepository.findById(fileId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found"));

    // Kiểm tra MIME type
    String mimeType = (file.getFileType() != null && file.getFileType().contains("/"))
        ? file.getFileType()
        : MediaType.APPLICATION_OCTET_STREAM_VALUE;

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.parseMediaType(mimeType));
    headers.setContentDisposition(ContentDisposition.attachment()
        .filename(file.getFileName(), StandardCharsets.UTF_8).build());

    return ResponseEntity.ok()
        .headers(headers)
        .body(file.getFileData());
  }
}
