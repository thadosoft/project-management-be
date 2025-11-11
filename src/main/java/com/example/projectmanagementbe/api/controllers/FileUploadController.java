package com.example.projectmanagementbe.api.controllers;

import com.example.projectmanagementbe.api.models.ReferenceFileV2;
import com.example.projectmanagementbe.api.models.dto.responses.FileUploadResponse;
import com.example.projectmanagementbe.api.services.FileUploadsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/v1/file-uploads")
@RestController
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadsService fileUploadsService;
    private static final String UPLOAD_DIR = "C://HLO"; //dường dẫn



    @GetMapping("/images/{filename:.+}") //chi lay ten file
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) throws IOException {
        // combine filename với UPLOAD_DIR để tạo đường dẫn access đầy đủ
        String filePath = UPLOAD_DIR + "/" + filename;
        byte[] imageBytes = fileUploadsService.getFileAsBytes(filePath);
        MediaType mediaType = fileUploadsService.determineMediaType(filePath);
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(imageBytes);
    }

    @GetMapping("/videos/**")
    public ResponseEntity<byte[]> getVideo(HttpServletRequest request,
                                           @RequestHeader(value = "Range", required = false) String httpRangeList) throws IOException {
        String videoPath = request.getRequestURI().split("/file-uploads/videos/")[1].replace("\\", "/");
        Path path = Paths.get(videoPath);
        String directory = Optional.ofNullable(path.getParent()).map(Path::toString).orElse("");
        String fileName = Optional.ofNullable(path.getFileName()).map(Path::toString).orElse("");
        int lastDotIndex = fileName.lastIndexOf('.');
        String fileType = (lastDotIndex != -1) ? fileName.substring(lastDotIndex + 1) : "";
        Path correctPath = Paths.get(directory, fileName);
        if (!Files.exists(correctPath)) {
            throw new IOException("File not found: " + correctPath);
        }
        return fileUploadsService.prepareContent(fileName, fileType, httpRangeList, directory);
    }

    @PostMapping(value = "/images/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<FileUploadResponse>> uploadImages(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam(value = "inventoryItemId", required = false) Long inventoryItemId,
            @RequestParam(value = "bookId", required = false) Long bookId) throws IOException {

        List<ReferenceFileV2> referenceFiles = fileUploadsService.uploadImages(files, inventoryItemId, bookId, UPLOAD_DIR);

        List<FileUploadResponse> responses = new ArrayList<>();
        for (ReferenceFileV2 referenceFile : referenceFiles) {
            FileUploadResponse response = new FileUploadResponse();
            response.setId(referenceFile.getId());
            response.setFileName(referenceFile.getFileName());
            response.setFileType(referenceFile.getFileType());
            response.setFileSize(referenceFile.getFileSize());
            response.setFilePath(referenceFile.getFilePath());
            response.setAccessUrl(referenceFile.getAccessUrl());
            responses.add(response);
        }

        return ResponseEntity.ok(responses);
    }
}