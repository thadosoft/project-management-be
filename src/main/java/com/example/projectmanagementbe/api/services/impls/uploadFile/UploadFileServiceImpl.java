package com.example.projectmanagementbe.api.services.impls.uploadFile;

import com.example.projectmanagementbe.api.models.referenceProfile.ReferenceFile;
import com.example.projectmanagementbe.api.models.referenceProfile.ReferenceProfile;
import com.example.projectmanagementbe.api.repositories.referenceProfile.ReferenceFileRepository;
import com.example.projectmanagementbe.api.repositories.referenceProfile.ReferenceProfileRepository;
import com.example.projectmanagementbe.api.services.referenceProfile.IFileUpload;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UploadFileServiceImpl implements IFileUpload {

  private final ReferenceFileRepository referenceFileRepository;
  private final ReferenceProfileRepository referenceProfileRepository;


  // ✅ Upload file theo referenceProfileId
  public void uploadFile(Long referenceProfileId, MultipartFile file) {
    ReferenceProfile profile = referenceProfileRepository.findById(referenceProfileId)
        .orElseThrow(() -> new RuntimeException("Reference Profile không tồn tại"));

    try {
      ReferenceFile referenceFile = new ReferenceFile();
      referenceFile.setReferenceProfile(profile);
      referenceFile.setFileName(file.getOriginalFilename());
      referenceFile.setFileType(file.getContentType());
      referenceFile.setFileSize(file.getSize());
      referenceFile.setFileData(file.getBytes());

      referenceFileRepository.save(referenceFile);
    } catch (IOException e) {
      throw new RuntimeException("Lỗi khi lưu file", e);
    }
  }

  public List<ReferenceFile> getFilesByProfile(Long referenceProfileId) {
    return referenceFileRepository.findByReferenceProfileId(referenceProfileId);
  }

  @Override
  public ResponseEntity<byte[]> downloadFile(Long fileId) {
    ReferenceFile file = referenceFileRepository.findById(fileId)
        .orElseThrow(() -> new RuntimeException("File not found"));

    // Kiểm tra MIME type
    String mimeType = (file.getFileType() != null && file.getFileType().contains("/"))
        ? file.getFileType()
        : MediaType.APPLICATION_OCTET_STREAM_VALUE;

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.parseMediaType(mimeType));
    headers.setContentDisposition(ContentDisposition.attachment()
        .filename(file.getFileName()).build());

    return ResponseEntity.ok()
        .headers(headers)
        .body(file.getFileData());
  }
}
