package com.example.projectmanagementbe.api.controllers;

import com.example.projectmanagementbe.api.services.FileUploadsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RequestMapping("/api/v1/file-uploads")
@RestController
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadsService fileUploadsService;

    @GetMapping("/images/**")
    public ResponseEntity<byte[]> getImage(HttpServletRequest request) throws IOException {
        String imagePath = request.getRequestURI().split("/file-uploads/images/")[1];

        byte[] imageBytes = fileUploadsService.getFileAsBytes(imagePath);
        MediaType mediaType = fileUploadsService.determineMediaType(imagePath);

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
}