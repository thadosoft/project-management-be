package com.example.projectmanagementbe.api.services.referenceProfile;

import com.example.projectmanagementbe.api.models.referenceProfile.ReferenceFile;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IFileUpload {

  ResponseEntity<byte[]> downloadFile(Long fileId);

  List<ReferenceFile> getFilesByProfile(Long referenceProfileId);

  void uploadFile(Long referenceProfileId, MultipartFile file);
}
