package com.example.projectmanagementbe.api.models.dto.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileUploadResponse {
    private Long id;
    private String fileName;
    private String fileType;
    private Long fileSize;
    private String filePath;
    private String accessUrl;
}