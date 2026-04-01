package com.example.projectmanagementbe.api.mappers;

import com.example.projectmanagementbe.api.models.lunch.Food;
import com.example.projectmanagementbe.api.models.dto.responses.lunch.FoodResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FoodMapper {

    // Map Camera entity -> CameraResponse DTO
    FoodResponse mapFoodResponse(Food food);
}
