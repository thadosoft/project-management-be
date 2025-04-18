package com.example.projectmanagementbe.api.services.impls;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import com.example.projectmanagementbe.api.services.FileUploadsService;
import com.example.projectmanagementbe.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Log4j2
public class FileUploadsServiceImpl implements FileUploadsService {

    @Override
    public byte[] getFileAsBytes(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            return Files.readAllBytes(path);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.FILE_NOT_FOUND.toString());
        }
    }

    @Override
    public MediaType determineMediaType(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        String contentType = Files.probeContentType(path);
        if (contentType != null) {
            return MediaType.parseMediaType(contentType);
        }
        return MediaType.APPLICATION_OCTET_STREAM;
    }

    @Override
    public ResponseEntity<byte[]> prepareContent(final String fileName, final String fileType, final String range, final String filePath) {
        try {
            final String fileKey = fileName ;
            long rangeStart = 0;
            long rangeEnd = 1024*1024;
            final Long fileSize = getFileSize(fileKey, filePath);

            if (range == null) {
                return buildResponse(fileKey, fileType, rangeStart, rangeEnd, fileSize, filePath);
            }

            // Parsing Range header
            String[] ranges = range.split("-");
            rangeStart = Long.parseLong(ranges[0].substring(6));
            if (ranges.length > 1) {
                rangeEnd = Long.parseLong(ranges[1]);
            } else {
                rangeEnd = rangeStart + 1024*1024;
            }

            rangeEnd = Math.min(rangeEnd, fileSize - 1);
            return buildResponse(fileKey, fileType, rangeStart, rangeEnd, fileSize, filePath);
        } catch (IOException e) {
            log.error("Exception while reading the file: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private ResponseEntity<byte[]> buildResponse(String fileKey, String fileType, long rangeStart, long rangeEnd, Long fileSize, String filePath) throws IOException {
        byte[] data = readByteRangeNew(fileKey, rangeStart, rangeEnd, filePath);
        String contentLength = String.valueOf((rangeEnd - rangeStart) + 1);
        HttpStatus httpStatus = rangeEnd >= fileSize ? HttpStatus.OK : HttpStatus.PARTIAL_CONTENT;

        return ResponseEntity.status(httpStatus)
                .header(HttpHeaders.CONTENT_TYPE, determineMediaType(fileType).toString())
                .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                .header(HttpHeaders.CONTENT_LENGTH, contentLength)
                .header(HttpHeaders.CONTENT_RANGE, "bytes " + rangeStart + "-" + rangeEnd + "/" + fileSize)
                .body(data);
    }

    @Override
    public byte[] readByteRangeNew(String filename, long start, long end, String filePath) throws IOException {
        Path path = Paths.get(filePath, filename);

        try (InputStream inputStream = Files.newInputStream(path)) {
            if (start > end) {
                throw new IllegalArgumentException("Start byte must be less than or equal to end byte.");
            }

            long fileSize = Files.size(path);
            if (start >= fileSize) {
                throw new IllegalArgumentException("Start byte is beyond the end of the file.");
            }

            if (end >= fileSize) {
                end = fileSize - 1;
            }

            byte[] result = new byte[(int) (end - start + 1)];

            inputStream.skip(start);
            int bytesRead = inputStream.read(result);

            if (bytesRead != result.length) {
                throw new IOException("Could not read the expected number of bytes from the file.");
            }

            return result;
        }
    }


    @Override
    public Long getFileSize(String fileName, String filePath) {
        return Optional.ofNullable(fileName)
                .map(file -> Paths.get(filePath, file))
                .map(this::sizeFromFile)
                .orElse(0L);
    }

    private Long sizeFromFile(Path path) {
        try {
            return Files.size(path);
        } catch (IOException ioException) {
            log.error("Error while getting the file size", ioException);
        }
        return 0L;
    }
}