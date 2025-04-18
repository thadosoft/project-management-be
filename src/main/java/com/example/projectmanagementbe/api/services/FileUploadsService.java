package com.example.projectmanagementbe.api.services;

import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public interface FileUploadsService {

    byte[] getFileAsBytes(String filePath) throws IOException;

    MediaType determineMediaType(String filePath) throws IOException;

    Long getFileSize(String fileName, String filePath);

    ResponseEntity<byte[]> prepareContent(final String fileName, final String fileType, final String range, String filePath);

    byte[] readByteRangeNew(String filename, long start, long end, String filePath) throws IOException;
}