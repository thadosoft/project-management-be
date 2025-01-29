package com.example.projectmanagementbe.api.services.impls;

import com.example.projectmanagementbe.api.models.dto.requests.MediaRequest;
import com.example.projectmanagementbe.api.models.dto.responses.MediaResponse;
import com.example.projectmanagementbe.api.models.Media;
import com.example.projectmanagementbe.api.mappers.MediaMapper;
import com.example.projectmanagementbe.api.repositories.MediaRepository;
import com.example.projectmanagementbe.api.services.IMediaService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MediaService implements IMediaService {

  private final MediaRepository mediaRepository;
  private final MediaMapper mediaMapper;

  @Override
  public List<MediaResponse> findAll() {
    return mediaRepository.findAll().stream().map(mediaMapper::toMediaResponse).toList();
  }

  @Override
  public void create(MediaRequest mediaRequest) {
    mediaRepository.save(mediaMapper.toMediaEntity(mediaRequest));
  }

  @Override
  public void update(String id, MediaRequest mediaRequest) {
    Media media = mediaRepository.findById(id).orElseThrow(() -> new RuntimeException("Media not found"));

    mediaMapper.toMediaEntity(mediaRequest, media);

    mediaRepository.save(media);
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
