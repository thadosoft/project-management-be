package com.example.projectmanagementbe.api.services.impls.uploadFileV2;

import com.example.projectmanagementbe.api.models.ReferenceFileV2;
import com.example.projectmanagementbe.api.models.iventory.InventoryItem;
import com.example.projectmanagementbe.api.repositories.inventory.InventoryItemRepository;
import com.example.projectmanagementbe.api.repositories.ReferenceFileV2Repository;
import com.example.projectmanagementbe.api.services.referenceProfile.IFileUploadV2;
import com.example.projectmanagementbe.exception.ErrorCode;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UploadFileV2ServiceImpl implements IFileUploadV2 {

  private final ReferenceFileV2Repository referenceFileV2Repository;
  private final InventoryItemRepository inventoryItemRepository;

  private static final String UPLOAD_DIR;

  static {
    Path projectRoot = Paths.get("").toAbsolutePath();
    UPLOAD_DIR = projectRoot.resolve("uploads").toString();
  }

  @PostConstruct
  public void init() {
    File dir = new File(UPLOAD_DIR);
    if (!dir.exists()) {
      boolean created = dir.mkdirs();//tao thu muc neu chua co
      if (created) {
        log.info("Tạo thư mục thành công: {}", UPLOAD_DIR);
      } else {
        log.error("Tạo thư mục upload thất bại: {}", UPLOAD_DIR);
        throw new RuntimeException("Tạo thư mục upload thất bại: " + UPLOAD_DIR);
      }
    } else {
      log.info("Thư muc upload đã tôồn tại: {}", UPLOAD_DIR);
    }
  }

  @Override
  public void uploadFile(Long inventoryItemId, MultipartFile file) {
    log.info("Đang upload fiole cho inventoryItemId: {}", inventoryItemId);
    if (file == null || file.isEmpty()) {
      log.error("File rỗng");
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorCode.FILE_EMPTY.toString());
    }

    log.info("File nhan duoc: name={}, size={}, contentType={}",
            file.getOriginalFilename(), file.getSize(), file.getContentType());

    InventoryItem inventoryItem = inventoryItemRepository.findById(inventoryItemId)
            .orElseThrow(() -> {
              log.error("Không tìm thấy id {} ", inventoryItemId);
              return new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy id của vật tư");
            });

    try {
      String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
      String filePath = UPLOAD_DIR + File.separator + fileName;
      log.info("Dang save: {}", filePath);

      File destFile = new File(filePath);
      file.transferTo(destFile);
      log.info("Save thanh cong!!!");

      ReferenceFileV2 referenceFile = new ReferenceFileV2();
      referenceFile.setInventoryItem(inventoryItem);
      referenceFile.setFileName(file.getOriginalFilename());
      referenceFile.setFileType(file.getContentType());
      referenceFile.setFileSize(file.getSize());
      referenceFile.setFilePath(filePath);

      referenceFileV2Repository.save(referenceFile);
      log.info("File save trong database: id={}", referenceFile.getId());
    } catch (IOException e) {
      log.error("Tai file that bai file: {}", e.getMessage(), e);
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.FILE_UPLOAD_FAILED.toString());
    }
  }

  @Override
  public ResponseEntity<byte[]> downloadFile(Long fileId) {
    log.info("Dang download file voi id: {}", fileId);
    ReferenceFileV2 file = referenceFileV2Repository.findById(fileId)
            .orElseThrow(() -> {
              log.error("Khong tim thay file!!!!!!", fileId);
              return new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
            });

    try {
      File fileToDownload = new File(file.getFilePath());
      if (!fileToDownload.exists()) {
        log.error("File ko ton tai!!!!!!!: {}", file.getFilePath());
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorCode.FILE_NOT_FOUND.toString());
      }

      byte[] fileData = Files.readAllBytes(fileToDownload.toPath());
      log.info("Doc file thanh cong!!!!!!");

      String mimeType = (file.getFileType() != null && file.getFileType().contains("/"))
              ? file.getFileType()
              : MediaType.APPLICATION_OCTET_STREAM_VALUE;

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.parseMediaType(mimeType));
      headers.setContentDisposition(ContentDisposition.attachment()
              .filename(file.getFileName()).build());

      return ResponseEntity.ok().headers(headers).body(fileData);
    } catch (IOException e) {
      log.error("Doc file that bai: {}", file.getFilePath(), e);
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.FILE_UPLOAD_FAILED.toString());
    }
  }

  @Override
  public List<ReferenceFileV2> getImagesByInventoryItem(Long inventoryItemId) {
    log.info("fetch hinh anh tu inventoryItemId: {}", inventoryItemId);
    return referenceFileV2Repository.findByInventoryItemId(inventoryItemId);
  }
}