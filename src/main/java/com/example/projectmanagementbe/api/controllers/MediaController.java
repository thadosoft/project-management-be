package com.example.projectmanagementbe.api.controllers;

import com.example.projectmanagementbe.api.models.dto.requests.MediaRequest;
import com.example.projectmanagementbe.api.models.dto.responses.MediaResponse;
import com.example.projectmanagementbe.api.services.IMediaService;
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

  private final IMediaService IMediaService;

  @GetMapping
  public ResponseEntity<List<MediaResponse>> findAll() {
    return ResponseEntity.ok(IMediaService.findAll());
  }

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody MediaRequest mediaRequest) {
    IMediaService.create(mediaRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable String id, @RequestBody MediaRequest mediaRequest) {
    IMediaService.update(id, mediaRequest);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    IMediaService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<MediaResponse> findById(@PathVariable String id) {
    return ResponseEntity.ok(IMediaService.findById(id));
  }
}
