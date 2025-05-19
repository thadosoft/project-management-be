package com.example.projectmanagementbe.api.controllers;

import com.example.projectmanagementbe.api.models.dto.requests.WhiteBoard.CreateWhiteBoard;
import com.example.projectmanagementbe.api.models.dto.requests.WhiteBoard.SearchWhiteBoard;
import com.example.projectmanagementbe.api.models.dto.requests.WhiteBoard.UpdateWhiteBoard;
import com.example.projectmanagementbe.api.models.dto.responses.WhiteBoardResponse;
import com.example.projectmanagementbe.api.services.WhiteBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/white-boards")
@RequiredArgsConstructor
public class WhiteBoardController {

  private final WhiteBoardService whiteBoardService;

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody CreateWhiteBoard request) {
    whiteBoardService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<WhiteBoardResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(whiteBoardService.findById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdateWhiteBoard request) {
    whiteBoardService.update(id, request);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    whiteBoardService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/search")
  public Page<WhiteBoardResponse> search(@RequestBody SearchWhiteBoard request, Pageable pageable) {
    return whiteBoardService.searchByParams(request, pageable);
  }
}
