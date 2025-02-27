package com.example.projectmanagementbe.api.controllers.referenceProfile;

import com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.ReferenceProfileRequest;
import com.example.projectmanagementbe.api.models.dto.requests.referenceProfile.Update.UpdateReferenceProfileRequest;
import com.example.projectmanagementbe.api.models.dto.responses.referenceProfile.ReferenceProfileResponse;
import com.example.projectmanagementbe.api.services.referenceProfile.IReferenceProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/api/v1/reference-profile")
@RequiredArgsConstructor
public class ReferenceProfileController {

  private final IReferenceProfileService profileService;

  @GetMapping
  public ResponseEntity<Page<ReferenceProfileResponse>> findAll(Pageable pageable) {
    return ResponseEntity.ok(profileService.findAll(pageable));
  }

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody ReferenceProfileRequest referenceProfileRequest) {
    profileService.create(referenceProfileRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdateReferenceProfileRequest updateReferenceProfileRequest) {
    profileService.update(id, updateReferenceProfileRequest);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    profileService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ReferenceProfileResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(profileService.findById(id));
  }
}
