package com.example.projectmanagementbe.api.services.impls;

import com.example.projectmanagementbe.api.models.ReferenceFileV2;
import com.example.projectmanagementbe.api.models.iventory.InventoryItem;
import com.example.projectmanagementbe.api.repositories.ReferenceFileV2Repository;
import com.example.projectmanagementbe.api.repositories.inventory.InventoryItemRepository;
import com.example.projectmanagementbe.exception.ErrorCode;
import com.example.projectmanagementbe.api.services.FileUploadsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class FileUploadsServiceImpl implements FileUploadsService {

    private final InventoryItemRepository inventoryItemRepository;
    private final ReferenceFileV2Repository referenceFileV2Repository;

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
            final String fileKey = fileName;
            long rangeStart = 0;
            long rangeEnd = 1024 * 1024;
            final Long fileSize = getFileSize(fileKey, filePath);

            if (range == null) {
                return buildResponse(fileKey, fileType, rangeStart, rangeEnd, fileSize, filePath);
            }

            String[] ranges = range.split("-");
            rangeStart = Long.parseLong(ranges[0].substring(6));
            if (ranges.length > 1) {
                rangeEnd = Long.parseLong(ranges[1]);
            } else {
                rangeEnd = rangeStart + 1024 * 1024;
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

    @Override
    public ReferenceFileV2 uploadImage(MultipartFile file, Long inventoryItemId, String uploadDir) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File rôỗng hoặc ko tìm thấy");
        }

        InventoryItem inventoryItem = inventoryItemRepository.findById(inventoryItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ko tìm tấy vật tư với id: " + inventoryItemId));

        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
        String uniqueFilename = UUID.randomUUID() + fileExtension;

        Path uploadPath = Paths.get(uploadDir).toAbsolutePath();
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(uniqueFilename);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        String contentType = file.getContentType() != null ? file.getContentType() : Files.probeContentType(filePath);

        ReferenceFileV2 referenceFile = new ReferenceFileV2();
        referenceFile.setInventoryItem(inventoryItem);
        referenceFile.setFileName(uniqueFilename);
        referenceFile.setFileType(contentType);
        referenceFile.setFileSize(file.getSize());
        referenceFile.setFilePath(filePath.toAbsolutePath().toString());
        referenceFile.setAccessUrl("/api/v1/file-uploads/images/" + uniqueFilename);

        referenceFile = referenceFileV2Repository.save(referenceFile);

        log.info("File uploaded thành công: {}", filePath);
        return referenceFile;
    }

    @Override
    public List<ReferenceFileV2> uploadImages(List<MultipartFile> files, Long inventoryItemId, String uploadDir) throws IOException {
        List<ReferenceFileV2> referenceFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            ReferenceFileV2 referenceFile = uploadImage(file, inventoryItemId, uploadDir);
            referenceFiles.add(referenceFile);
        }
        return referenceFiles;
    }
}