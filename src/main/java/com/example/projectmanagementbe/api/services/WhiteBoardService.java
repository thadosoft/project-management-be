package com.example.projectmanagementbe.api.services;

import com.example.projectmanagementbe.api.models.dto.requests.WhiteBoard.CreateWhiteBoard;
import com.example.projectmanagementbe.api.models.dto.requests.WhiteBoard.SearchWhiteBoard;
import com.example.projectmanagementbe.api.models.dto.requests.WhiteBoard.UpdateWhiteBoard;
import com.example.projectmanagementbe.api.models.dto.responses.WhiteBoardResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WhiteBoardService {
    Page<WhiteBoardResponse> searchByParams(SearchWhiteBoard request, Pageable pageable);

    void create(CreateWhiteBoard request);

    void update(Long id, UpdateWhiteBoard request);

    void delete(Long id);

    WhiteBoardResponse findById(Long id);
}
