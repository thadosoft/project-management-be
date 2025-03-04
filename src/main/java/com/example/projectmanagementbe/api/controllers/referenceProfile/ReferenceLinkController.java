package com.example.projectmanagementbe.api.controllers.referenceProfile;

import com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.ReferenceLinkRequest;
import com.example.projectmanagementbe.api.services.referenceProfile.IProfileReferenceLink;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reference-links")
@RequiredArgsConstructor
public class ReferenceLinkController {

  private final IProfileReferenceLink referenceLink;

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody ReferenceLinkRequest request) {
    referenceLink.createReferenceLink(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
