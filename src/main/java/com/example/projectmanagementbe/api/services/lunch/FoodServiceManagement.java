package com.example.projectmanagementbe.api.services.lunch;

import com.example.projectmanagementbe.api.mappers.FoodMapper;
import com.example.projectmanagementbe.api.models.lunch.Food;
import com.example.projectmanagementbe.api.models.dto.requests.lunch.FoodRequest;
import com.example.projectmanagementbe.api.models.dto.responses.lunch.FoodResponse;
import com.example.projectmanagementbe.api.repositories.FoodRepository;
import com.example.projectmanagementbe.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodServiceManagement implements FoodService {

    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;

    @Override
    public List<FoodResponse> findAll() {
        return foodRepository.findAll().stream()
                .map(foodMapper::mapFoodResponse)
                .toList();
    }

    @Override
    public FoodResponse findById(Long id) {
        return foodRepository.findById(id)
                .map(foodMapper::mapFoodResponse)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                ErrorCode.FOOD_NOT_FOUND.getMessage()
                        )
                );
    }

    @Override
    public Page<FoodResponse> findByParams(FoodRequest request, Pageable pageable) {
        String name = request.getName();
        String category = request.getCategory();

        boolean noFilter =
                (name == null || name.isBlank())
                        && (category == null || category.isBlank());

        Page<Food> result;

        if (noFilter) {
            result = foodRepository.findAll(pageable);
        } else {
            result = foodRepository.findByParams(name, category, pageable);
        }

        return result.map(foodMapper::mapFoodResponse);
    }
}