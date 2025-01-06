package com.example.projectmanagementbe.controllers;

import com.example.projectmanagementbe.dto.requests.creates.MediaCreateRequest;
import com.example.projectmanagementbe.dto.requests.updates.MediaUpdateRequest;
import com.example.projectmanagementbe.dto.responses.MediaResponse;
import com.example.projectmanagementbe.services.MediaService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/medias")
@RequiredArgsConstructor
public class MediaController {

  private final MediaService mediaService;

  @GetMapping
  public ResponseEntity<List<MediaResponse>> findAll() {
    return ResponseEntity.ok(mediaService.findAll());
  }

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody MediaCreateRequest mediaCreateRequest) {
    mediaService.create(mediaCreateRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable String id, @RequestBody MediaUpdateRequest mediaUpdateRequest) {
    mediaService.update(id, mediaUpdateRequest);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    mediaService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<MediaResponse> findById(@PathVariable String id) {
    return ResponseEntity.ok(mediaService.findById(id));
  }
}
