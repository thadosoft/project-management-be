package com.example.projectmanagementbe.api.controllers.project;

import com.example.projectmanagementbe.api.models.dto.requests.project.MediaRequest;
import com.example.projectmanagementbe.api.models.dto.responses.project.MediaResponse;
import com.example.projectmanagementbe.api.services.project.IMediaService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/medias")
@RequiredArgsConstructor
public class MediaController {

  private final IMediaService mediaService;

  @GetMapping
  public ResponseEntity<Resource> download(
      @RequestParam("projectName") String projectName,
      @RequestParam("fileName") String fileName
  ) throws IOException {
    Path filePath = Paths.get("D://STORAGE/" + projectName).resolve(fileName).normalize();

    if (!Files.exists(filePath)) {
      return ResponseEntity.notFound().build();
    }

    Resource resource = new UrlResource(filePath.toUri());

    MediaResponse media = mediaService.findByName(fileName.split("_", 3)[2]);
    LocalDateTime uploadedDate = media.getEntryDate();

    String contentType = Files.probeContentType(filePath);
    if (contentType == null) {
      contentType = "application/octet-stream";
    }

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(contentType))
        .header(HttpHeaders.LAST_MODIFIED, "String.valueOf(uploadedDate)")
        .body(resource);
  }

  @GetMapping(path = "/attachments")
  public ResponseEntity<List<String>> downloadAttachFiles(
      @RequestParam("projectName") String projectName,
      @RequestParam("assignmentId") String assignmentId
  ) throws IOException {
    Path projectPath = Paths.get("D://STORAGE/" + projectName);

    if (!Files.exists(projectPath)) {
      return ResponseEntity.notFound().build();
    }

    try (Stream<Path> fileStream = Files.list(projectPath)) {
      String pattern = assignmentId + "_attach_*";

      List<String> fileNames = fileStream
          .filter(Files::isRegularFile)
          .map(Path::getFileName)
          .map(Path::toString)
          .filter(fileName -> fileName.matches(pattern.replace("*", ".*")))
          .collect(Collectors.toList());

//      for (String fileName : fileNames) {
//        System.out.println(mediaService.findByAssignment_IdAndName(assignmentId, fileName.split("_")[2]).getEntryDate());
//      }

      return ResponseEntity.ok(fileNames);
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<Map<String, String>> upload(
      @RequestParam("file") MultipartFile file,
      @RequestParam("assignmentId") String assignmentId,
      @RequestParam("projectName") String projectName,
      @RequestParam("isContent") boolean isContent
  ) throws IOException {
    return ResponseEntity.ok(mediaService.upload(new MediaRequest(projectName, file, assignmentId, isContent)));
  }
}
