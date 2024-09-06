package com.example.getiryemek_clone.service;

import com.example.getiryemek_clone.dto.response.FoodResponse;
import com.example.getiryemek_clone.entity.update.FoodUpdateDto;
import com.example.getiryemek_clone.dto.request.FoodDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FoodService  {

    ApiResponse<List<FoodResponse>> getAllFoods() throws InterruptedException;

    ApiResponse<List<FoodResponse>> getFoodFromRestaurant(Long restaurantId);

    ApiResponse<FoodResponse> findFoodById(Long foodId) throws InterruptedException;

    ApiResponse<List<FoodResponse>> getFoodByCategory(Long categoryId);

    ApiResponse<List<FoodResponse>> findFoodByName(String foodName);

    ApiResponse<List<FoodResponse>> foodWithCategoryAndRestaurant(Long restaurantId, Long categoryId);

    ApiResponse<FoodResponse> add(FoodDto foodDto, Long categoryId, Long restaurantId);

    ApiResponse<FoodResponse> update(Long foodId, FoodUpdateDto updateDto);

    ApiResponse<FoodResponse> deleteFood(Long foodId);
}
