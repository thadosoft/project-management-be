package com.example.projectmanagementbe.api.services.project;

import com.example.projectmanagementbe.api.models.dto.requests.project.MediaRequest;
import com.example.projectmanagementbe.api.models.dto.responses.project.MediaResponse;
import java.io.IOException;
import java.util.Map;

public interface IMediaService {

//  Resource download(MediaRequest mediaRequest) throws IOException;

  Map<String, String> upload(MediaRequest mediaRequest) throws IOException;

  MediaResponse findByName(String mediaName) throws IOException;
}
