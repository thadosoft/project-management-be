package com.example.projectmanagementbe.api.controllers.project;

import com.example.projectmanagementbe.api.models.dto.requests.project.MediaRequest;
import com.example.projectmanagementbe.api.services.project.IMediaService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/medias")
@RequiredArgsConstructor
public class MediaController {

  private final IMediaService mediaService;

  @PostMapping(path = "/download")
  public ResponseEntity<Resource> download(@RequestBody MediaRequest mediaRequest) throws IOException {
    Resource resource = mediaService.download(mediaRequest);


    if (resource == null) {
      return ResponseEntity.notFound().build();
    }

    String contentType = Files.probeContentType(Paths.get(resource.getURI()));
    if (contentType == null) {
      contentType = "application/octet-stream";
    }

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(contentType))
        .body(resource);
  }

  @GetMapping(path = "/uploaded-date")
  public ResponseEntity<LocalDateTime> getUploadedDateByName(@RequestParam("fileName") String fileName) throws IOException {
    return ResponseEntity.ok(mediaService.getUploadedDateByName(fileName));
  }

  @PostMapping(path = "/attachments")
  public ResponseEntity<List<String>> downloadAttachFiles(@RequestBody MediaRequest mediaRequest) throws IOException {
    return ResponseEntity.ok(mediaService.downloadAttachFiles(mediaRequest));
  }

  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<Map<String, String>> upload(@RequestParam("file") MultipartFile file, @RequestParam("assignmentId") String assignmentId, @RequestParam("projectName") String projectName, @RequestParam("isContent") boolean isContent) throws IOException {
    return ResponseEntity.ok(mediaService.upload(new MediaRequest(projectName, file, assignmentId, isContent)));
  }

  @DeleteMapping
  public ResponseEntity<Void> delete(@RequestBody MediaRequest mediaRequest) throws IOException {
    mediaService.delete(mediaRequest);
    return ResponseEntity.noContent().build();
  }
}
