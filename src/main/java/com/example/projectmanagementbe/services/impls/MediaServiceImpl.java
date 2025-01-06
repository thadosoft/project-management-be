package com.example.projectmanagementbe.services.impls;

import com.example.projectmanagementbe.dto.requests.creates.MediaCreateRequest;
import com.example.projectmanagementbe.dto.requests.updates.MediaUpdateRequest;
import com.example.projectmanagementbe.dto.responses.MediaResponse;
import com.example.projectmanagementbe.entities.MediaEntity;
import com.example.projectmanagementbe.mappers.MediaMapper;
import com.example.projectmanagementbe.repositories.MediaRepository;
import com.example.projectmanagementbe.services.MediaService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

  private final MediaRepository mediaRepository;
  private final MediaMapper mediaMapper;

  @Override
  public List<MediaResponse> findAll() {
    return mediaRepository.findAll().stream().map(mediaMapper::toMediaResponse).toList();
  }

  @Override
  public void create(MediaCreateRequest mediaCreateRequest) {
    mediaRepository.save(mediaMapper.toMediaEntity(mediaCreateRequest));
  }

  @Override
  public void update(String id, MediaUpdateRequest mediaUpdateRequest) {
    MediaEntity mediaEntity = mediaRepository.findById(id).orElseThrow(() -> new RuntimeException("Media not found"));

    mediaMapper.toMediaEntity(mediaUpdateRequest, mediaEntity);

    mediaRepository.save(mediaEntity);
  }

  @Override
  public void delete(String id) {
    if (mediaRepository.existsById(id)) {
      mediaRepository.deleteById(id);
    } else {
      throw new RuntimeException("Media not found");
    }
  }

  @Override
  public MediaResponse findById(String id) {
    return mediaMapper.toMediaResponse(mediaRepository.findById(id).orElseThrow(() -> new RuntimeException("Media not found")));
  }
}
