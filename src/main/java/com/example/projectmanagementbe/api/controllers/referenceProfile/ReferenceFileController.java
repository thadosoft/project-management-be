package com.example.projectmanagementbe.api.controllers.referenceProfile;

import com.example.projectmanagementbe.api.models.referenceProfile.ReferenceFile;
import com.example.projectmanagementbe.api.services.referenceProfile.IFileUpload;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/reference-files")
@RequiredArgsConstructor
public class ReferenceFileController {
  private final IFileUpload downloadFile;

  @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> uploadFile(
      @RequestParam("referenceProfileId") Long referenceProfileId,
      @RequestParam("file") MultipartFile file) {
    downloadFile.uploadFile(referenceProfileId, file);
    return ResponseEntity.ok("File uploaded successfully!");
  }

  @GetMapping("/profile/{referenceProfileId}")
  public ResponseEntity<List<ReferenceFile>> getFilesByProfile(@PathVariable Long referenceProfileId) {
    List<ReferenceFile> files = downloadFile.getFilesByProfile(referenceProfileId);
    return ResponseEntity.ok(files);
  }

  @GetMapping("/download/{fileId}")
  public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId) {
    return downloadFile.downloadFile(fileId);
  }
}
