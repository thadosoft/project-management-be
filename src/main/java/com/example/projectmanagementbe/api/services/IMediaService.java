package com.example.projectmanagementbe.api.services;

import com.example.projectmanagementbe.api.models.dto.requests.MediaRequest;
import com.example.projectmanagementbe.api.models.dto.responses.MediaResponse;
import java.util.List;

public interface IMediaService {
  List<MediaResponse> findAll();

  void create(MediaRequest mediaRequest);

  void update(String id, MediaRequest MediaRequest);

  void delete(String id);

  MediaResponse findById(String id);
}
