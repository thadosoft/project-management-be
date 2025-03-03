package com.example.projectmanagementbe.api.services.project;

import com.example.projectmanagementbe.api.models.dto.requests.project.MediaRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.core.io.Resource;

public interface IMediaService {

  Resource download(MediaRequest mediaRequest) throws IOException;

  LocalDateTime getUploadedDateByName(String fileName) throws IOException;

  List<String> downloadAttachFiles(MediaRequest mediaRequest) throws IOException;

  Map<String, String> upload(MediaRequest mediaRequest) throws IOException;

  void delete(MediaRequest mediaRequest) throws IOException;
}
