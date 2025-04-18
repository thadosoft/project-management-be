package com.example.projectmanagementbe.api.services.referenceProfile;

import com.example.projectmanagementbe.api.models.ReferenceFileV2;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IFileUploadV2 {

  void uploadFile(Long inventoryItemId, MultipartFile file);

  ResponseEntity<byte[]> downloadFile(Long fileId);

  List<ReferenceFileV2> getImagesByInventoryItem(Long inventoryItemId);
}