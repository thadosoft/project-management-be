package com.example.projectmanagementbe.api.controllers.lunch;

import com.example.projectmanagementbe.api.models.dto.requests.lunch.FoodRequest;
import com.example.projectmanagementbe.api.models.dto.responses.lunch.FoodResponse;
import com.example.projectmanagementbe.api.services.lunch.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @PostMapping("/search")
    public Page<FoodResponse> search(@RequestBody FoodRequest request, Pageable pageable) {
        return foodService.findByParams(request, pageable);
    }

    @GetMapping
    public ResponseEntity<List<FoodResponse>> getAll() {
        return ResponseEntity.ok(foodService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(foodService.findById(id));
    }
}