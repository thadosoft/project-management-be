package com.example.projectmanagementbe.api.services.impls.project;

import com.example.projectmanagementbe.api.models.project.Media;
import com.example.projectmanagementbe.api.models.dto.requests.project.MediaRequest;
import com.example.projectmanagementbe.api.mappers.project.MediaMapper;
import com.example.projectmanagementbe.api.models.dto.responses.project.MediaResponse;
import com.example.projectmanagementbe.api.repositories.project.MediaRepository;
import com.example.projectmanagementbe.api.services.project.IMediaService;
import com.example.projectmanagementbe.auth.configs.application.StorageConfig;
import jakarta.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MediaService implements IMediaService {

  private final MediaRepository mediaRepository;
  private final MediaMapper mediaMapper;
  private final StorageConfig storageConfig;

//  @Override
//  public Resource download(MediaRequest mediaRequest) throws IOException {
//    Path filePath = Paths.get("D://STORAGE/" + mediaRequest.getProjectName()).resolve(mediaRequest.getFile().getName()).normalize();
//
//    Resource resource = new UrlResource(filePath.toUri());
//
//    return resource;
//  }

  @Override
  public Map<String, String> upload(MediaRequest mediaRequest) throws IOException {
    if (mediaRequest.getFile().isEmpty()) {
      throw new RuntimeException("File is empty");
    }

    Map<String, String> responses = new HashMap<>();

    Media media = new Media();
    media.setName(mediaRequest.getFile().getOriginalFilename());
    media.setType(mediaRequest.getFile().getContentType());
    media.setPath(storageConfig.getDirectory() + File.separator + mediaRequest.getProjectName() + File.separator + mediaRequest.getFile().getOriginalFilename());
    media.setSize(mediaRequest.getFile().getSize());

    int temp = 1;
    while (mediaRepository.existsByAssignment_IdAndName(mediaRequest.getAssignmentId(), media.getName())) {
      media.setName(mediaRequest.getFile().getOriginalFilename().split("\\.")[0] + " (" + temp + ")." + mediaRequest.getFile().getOriginalFilename().split("\\.")[1]);
      temp++;
    }

    mediaMapper.toMediaEntity(mediaRequest, media);

    mediaRepository.save(media);

    String fileName = media.getName();
    fileName = mediaRequest.isContent() ? mediaRequest.getAssignmentId() + "_" + fileName : mediaRequest.getAssignmentId() + "_attach_" + fileName;

    Path savePath = Paths.get(
        storageConfig.getDirectory() +
            File.separator +
            mediaRequest.getProjectName() +
            File.separator + fileName
    );

    Files.write(savePath, mediaRequest.getFile().getBytes());

    responses.put("url", "http://localhost:8080/api/v1/medias?projectName=" + mediaRequest.getProjectName() + "&fileName=" + fileName);

    return responses;
  }

  @Override
  public MediaResponse findByName(String mediaName) throws IOException {
    return mediaMapper.toMediaResponse(mediaRepository.findByName((mediaName)));
  }
}
