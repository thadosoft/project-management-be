package com.example.projectmanagementbe.api.services.lunch;
import com.example.projectmanagementbe.api.models.dto.requests.lunch.FoodRequest;
import com.example.projectmanagementbe.api.models.dto.responses.lunch.FoodResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FoodService {

    Page<FoodResponse> findByParams(FoodRequest request, Pageable pageable);

    List<FoodResponse> findAll();

    FoodResponse findById(Long id);
}