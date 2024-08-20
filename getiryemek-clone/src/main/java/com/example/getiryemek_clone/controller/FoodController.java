package com.example.getiryemek_clone.controller;

import com.example.getiryemek_clone.dto.response.FoodResponse;
import com.example.getiryemek_clone.entity.Food;
import com.example.getiryemek_clone.dto.request.FoodDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.entity.update.FoodUpdateDto;
import com.example.getiryemek_clone.service.FoodService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/food")
public class FoodController {

    private final FoodService foodService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllFood(){
        ApiResponse<List<Food>> response = foodService.getAllFoods();
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{foodId}")
    public ResponseEntity<ApiResponse> getFoodById(@PathVariable Long foodId){

        ApiResponse<Food> response = foodService.findFoodById(foodId);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/foodFromRestaurant")
    public ResponseEntity<ApiResponse> getFoodsFromRestaurant(@RequestParam Long restaurantId){
        ApiResponse<List<Food>> response = foodService.getFoodFromRestaurant(restaurantId);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{foodName}")
    public ResponseEntity<ApiResponse> getFoodByName(@PathVariable String foodName){
            ApiResponse<Food> response = foodService.findFoodByName(foodName);
            return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/foodWithCategory/{id}")
    public ResponseEntity<ApiResponse> getFoodListByCategoryId(@PathVariable Long id){
        ApiResponse<List<Food>> response = foodService.getFoodByCategory(id);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    //@GetMapping("/foodWithRestaurantAndCategory")
    @GetMapping("/byRestaurantAndCategory")
    public ResponseEntity<ApiResponse> foodWithRestaurantAndCategory(
            @RequestParam Long restaurantId,
            @RequestParam Long categoryId){
        ApiResponse<List<Food>>  response = foodService
                .foodWithCategoryAndRestaurant(restaurantId,categoryId);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> addFood(@RequestBody FoodDto foodDto ,
                                               @RequestParam Long categoryId ,
                                               @RequestParam Long restaurantId){
        ApiResponse<FoodResponse> response = foodService.add(foodDto , categoryId , restaurantId);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @PutMapping("/{foodId}")
    public ResponseEntity<ApiResponse> updateFood(@PathVariable Long foodId , @RequestBody FoodUpdateDto updateDto){
        ApiResponse<Food> response=foodService.update(foodId , updateDto);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{foodId}")
    public ResponseEntity<ApiResponse> deleteFood(@PathVariable Long foodId){
        ApiResponse<Food> response = foodService.deleteFood(foodId);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }

}
