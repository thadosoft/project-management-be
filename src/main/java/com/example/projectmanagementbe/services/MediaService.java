package com.example.projectmanagementbe.services;

import com.example.projectmanagementbe.dto.requests.creates.MediaCreateRequest;
import com.example.projectmanagementbe.dto.requests.updates.MediaUpdateRequest;
import com.example.projectmanagementbe.dto.responses.MediaResponse;
import java.util.List;

public interface MediaService {
  List<MediaResponse> findAll();

  void create(MediaCreateRequest mediaCreateRequest);

  void update(Long id, MediaUpdateRequest MediaUpdateRequest);

  void delete(Long id);

  MediaResponse findById(Long id);
}
