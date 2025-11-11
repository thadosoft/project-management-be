package com.example.projectmanagementbe.api.services;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import com.example.projectmanagementbe.api.models.ReferenceFileV2;

import java.io.IOException;
import java.util.List;

public interface FileUploadsService {
    byte[] getFileAsBytes(String filePath) throws IOException;
    MediaType determineMediaType(String filePath) throws IOException;
    ResponseEntity<byte[]> prepareContent(String fileName, String fileType, String range, String filePath) throws IOException;
    byte[] readByteRangeNew(String filename, long start, long end, String filePath) throws IOException;
    Long getFileSize(String fileName, String filePath);

    // New method for file upload
    ReferenceFileV2 uploadImage(MultipartFile file, Long inventoryItemId, Long bookId, String uploadDir) throws IOException;
    List<ReferenceFileV2> uploadImages(List<MultipartFile> files, Long inventoryItemId, Long bookId, String uploadDir) throws IOException;
}