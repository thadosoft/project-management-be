package com.example.projectmanagementbe.api.controllers.referenceProfile;

import com.example.projectmanagementbe.api.models.ReferenceFileV2;
import com.example.projectmanagementbe.api.services.referenceProfile.IFileUploadV2;
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
@RequestMapping("/api/v1/files-materials")
@RequiredArgsConstructor
public class ReferenceFileV2Controller {

    private final IFileUploadV2 fileUploadService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)//http://localhost:8080/api/v1/files-materials/upload
    public ResponseEntity<String> uploadFile(
            @RequestParam("inventoryItemId") Long inventoryItemId,
            @RequestParam("file") MultipartFile file) {
        fileUploadService.uploadFile(inventoryItemId, file);
        return ResponseEntity.ok("File uploaded successfully");
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId) {
        return fileUploadService.downloadFile(fileId);
    }

    @GetMapping("/inventory-item/{inventoryItemId}")//http://localhost:8080/api/v1/files-materials/inventory-item/2
    public ResponseEntity<List<ReferenceFileV2>> getImagesByInventoryItem(@PathVariable Long inventoryItemId) {
        return ResponseEntity.ok(fileUploadService.getImagesByInventoryItem(inventoryItemId));
    }
}