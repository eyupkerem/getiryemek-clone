package com.example.getiryemek_clone.service;

import com.example.getiryemek_clone.dto.response.FoodResponse;
import com.example.getiryemek_clone.entity.Category;
import com.example.getiryemek_clone.entity.Food;
import com.example.getiryemek_clone.entity.Restaurant;
import com.example.getiryemek_clone.entity.update.FoodUpdateDto;
import com.example.getiryemek_clone.mapper.FoodMapper;
import com.example.getiryemek_clone.repository.CategoryRepository;
import com.example.getiryemek_clone.repository.FoodRepository;
import com.example.getiryemek_clone.dto.request.FoodDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;
    private final CategoryRepository categoryRepository;
    private final RestaurantRepository restaurantRepository;


    public ApiResponse<List<Food>> getAllFoods() {
       List<Food> foodList= foodRepository.findAll();
       return ApiResponse.success("Food list founded" , foodList);
    }

    public ApiResponse<Food> findFoodById(Long foodId) {
        return foodRepository.findById(foodId)
                .map(food -> ApiResponse.success("Food founded", food))
                .orElseGet(() -> ApiResponse.failure("Food could not found"));
    }

    public ApiResponse<Food> findFoodByName(String foodName){
        return foodRepository.findByName(foodName)
                .map(food -> ApiResponse.success("Food founded", food))
                .orElseGet(() -> ApiResponse.failure("Food could not found"));
    }

    public ApiResponse<FoodResponse>  add(FoodDto foodDto , Long categoryId , Long restaurantId) {
        Category category  =categoryRepository.findById(categoryId).orElseThrow(
                ()->new RuntimeException("Category could not found !"));
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                ()->new RuntimeException("Restaurant could not found ! "));
        Food newFood = foodMapper.toFood(foodDto);
        newFood.setCategory(category);
        newFood.setRestaurant(restaurant);
        foodRepository.save(newFood);
        return  ApiResponse.success("Food added succesfully" , foodMapper.toFoodResponse(newFood));
    }

    public ApiResponse<Food> deleteFood(Long foodId) {
        return foodRepository.findById(foodId)
                .map(food -> {
                    foodRepository.deleteById(foodId);
                    return ApiResponse.success("Food deleted succesfully" , food);
                })
                .orElseGet(()->ApiResponse.failure("Food could not deleted"));
    }

    public ApiResponse<Food> update(Long foodId, FoodUpdateDto updateDto) {

        if (    updateDto.getName() == null ||
                updateDto.getName().isEmpty() ||
                updateDto.getPrice() == null) {
            return ApiResponse.failure("All fields must be non-empty");
        }

        return foodRepository.findById(foodId)
                .map(
                        tempFood -> {
                            tempFood.setName(updateDto.getName());
                            tempFood.setPrice(updateDto.getPrice());
                            tempFood.setImageURL(updateDto.getImageURL());
                            tempFood.setIngredients(updateDto.getIngredients());
                            //update Category
                            foodRepository.save(tempFood);
                            return ApiResponse.success("Food updated succesfully", tempFood);
                        }
                )
                .orElseGet(() -> ApiResponse.failure("Food could not updated"));
    }
    public ApiResponse<List<Food>> getFoodFromRestaurant(Long restaurantId) {
        List<Food> foodList =  foodRepository.getFoodFromRestaurant(restaurantId)
                .stream().collect(Collectors.toList());
        return ApiResponse.success(" Food List founded from Restaurant ",foodList);
    }

    public ApiResponse<List<Food>> getFoodByCategory(Long categoryId) {
        Optional<List<Food>> optionalFoodList = foodRepository.findByCategoryId(categoryId);

        if (optionalFoodList.isEmpty()) {
            return ApiResponse.failure("Food list cannot be found");
        }

        List<Food> foodList = optionalFoodList.get();
        return ApiResponse.success("Food list found", foodList);
    }

    public ApiResponse<List<Food>> foodWithCategoryAndRestaurant(Long restaurantId, Long categoryId) {
        List<Food> foodList = foodRepository
                .getFoodByRestaurantAndCategory(restaurantId,categoryId) .stream().collect(Collectors.toList());

        if (foodList.isEmpty()) {
            return ApiResponse.failure("No food found for the given restaurant and category");
        }
        return ApiResponse.success("Food list found for the given restaurant and category", foodList);

    }
}
