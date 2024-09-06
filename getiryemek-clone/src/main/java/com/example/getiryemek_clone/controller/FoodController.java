package com.example.getiryemek_clone.controller;

import com.example.getiryemek_clone.dto.response.FoodResponse;
import com.example.getiryemek_clone.dto.request.FoodDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.entity.Food;
import com.example.getiryemek_clone.entity.update.FoodUpdateDto;
import com.example.getiryemek_clone.service.FoodService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@RequestMapping("/api/food")
public class FoodController {

    private final FoodService foodService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllFood() throws InterruptedException {
        ApiResponse<List<FoodResponse>> response = foodService.getAllFoods();
        return response.isSuccess() ? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PreAuthorize("hasAnyAuthority('RESTAURANT_ADMIN' , 'ADMIN')")
    @GetMapping("/{foodId}")
    public ResponseEntity<ApiResponse> getFoodById(@PathVariable Long foodId) throws InterruptedException {
        ApiResponse<FoodResponse> response = foodService.findFoodById(foodId);
        return response.isSuccess() ? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'RESTAURANT_ADMIN' , 'ADMIN')")
    @GetMapping("/by-restaurant/{restaurantId}")
    public ResponseEntity<ApiResponse> getFoodsFromRestaurant(@PathVariable Long restaurantId){
        ApiResponse<List<FoodResponse>> response = foodService.getFoodFromRestaurant(restaurantId);
        return response.isSuccess() ? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'RESTAURANT_ADMIN' , 'ADMIN')")
    @GetMapping("/byCategory/{categoryId}")
    public ResponseEntity<ApiResponse> getFoodsByCategory(@PathVariable Long categoryId){
        ApiResponse<List<FoodResponse>> response = foodService.getFoodByCategory(categoryId);
        return response.isSuccess() ? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'RESTAURANT_ADMIN' , 'ADMIN')")
    @GetMapping("/name/{foodName}")
    public ResponseEntity<ApiResponse> getFoodByName(@PathVariable String foodName){
        ApiResponse<List<FoodResponse>> response = foodService.findFoodByName(foodName);
        return response.isSuccess() ? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'RESTAURANT_ADMIN' , 'ADMIN')")
    @GetMapping("/byRestaurantAndCategory/{restaurantId}/category/{categoryId}")
    public ResponseEntity<ApiResponse> byRestaurantAndCategory(@PathVariable Long restaurantId
            , @PathVariable Long categoryId){
        ApiResponse<List<FoodResponse>> response = foodService
                .foodWithCategoryAndRestaurant(restaurantId, categoryId);
        return response.isSuccess() ? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PreAuthorize("hasAnyAuthority('RESTAURANT_ADMIN' , 'ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse> addFood(@RequestBody FoodDto foodDto,
                                               @RequestParam Long categoryId,
                                               @RequestParam Long restaurantId){
        ApiResponse<FoodResponse> response = foodService.add(foodDto, categoryId, restaurantId);
        return response.isSuccess() ? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PreAuthorize("hasAnyAuthority('RESTAURANT_ADMIN' , 'ADMIN')")
    @PutMapping("/{foodId}")
    public ResponseEntity<ApiResponse> updateFood(@PathVariable Long foodId
            , @RequestBody FoodUpdateDto updateDto){
        ApiResponse<FoodResponse> response = foodService.update(foodId, updateDto);
        return response.isSuccess() ? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PreAuthorize("hasAnyAuthority('RESTAURANT_ADMIN' , 'ADMIN')")
    @DeleteMapping("/{foodId}")
    public ResponseEntity<ApiResponse> deleteFood(@PathVariable Long foodId){
        ApiResponse<FoodResponse> response = foodService.deleteFood(foodId);
        return response.isSuccess() ? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
