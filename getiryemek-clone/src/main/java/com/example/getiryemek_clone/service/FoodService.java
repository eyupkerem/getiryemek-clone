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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;
    private final CategoryRepository categoryRepository;
    private final RestaurantRepository restaurantRepository;


    public ApiResponse<List<FoodResponse>> getAllFoods() {
       List<FoodResponse> foodList= foodRepository.findAll()
               .stream()
               .map(food -> foodMapper.toFoodResponse(food))
               .collect(Collectors.toList());
       return ApiResponse.success("Food list founded" , foodList);
    }

    public ApiResponse<FoodResponse> findFoodById(Long foodId) {
        return foodRepository.findById(foodId)
                .map(food -> ApiResponse.success("Food founded", foodMapper.toFoodResponse(food)))
                .orElseGet(() -> ApiResponse.failure("Food could not found"));
    }

    public ApiResponse<List<FoodResponse>> findFoodByName(String foodName) {
        List<FoodResponse> foodList = foodRepository.findByName(foodName)
                .stream()
                .map(food -> foodMapper.toFoodResponse(food))
                .collect(Collectors.toList());

        if (foodList.isEmpty()) {
            return ApiResponse.failure("No food found with the given name");
        }

        return ApiResponse.success("Food(s) found", foodList);
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

    public ApiResponse<FoodResponse> deleteFood(Long foodId) {
        return foodRepository.findById(foodId)
                .map(food -> {
                    foodRepository.deleteById(foodId);
                    return ApiResponse.success("Food deleted succesfully" ,  foodMapper.toFoodResponse(food));
                })
                .orElseGet(()->ApiResponse.failure("Food could not deleted"));
    }

    public ApiResponse<FoodResponse> update(Long foodId, FoodUpdateDto updateDto) {

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
                            tempFood.setPrice(updateDto.getPrice());
                            tempFood.setImageUrl(updateDto.getImageURL());
                            tempFood.setIngredients(updateDto.getIngredients());
                            foodRepository.save(tempFood);
                            return ApiResponse.success("Food updated succesfully",
                                    foodMapper.toFoodResponse(tempFood));
                        }
                )
                .orElseGet(() -> ApiResponse.failure("Food could not updated"));
    }
    public ApiResponse<List<FoodResponse>> getFoodFromRestaurant(Long restaurantId) {
        List<FoodResponse> foodList =  foodRepository.getFoodFromRestaurant(restaurantId)
                .stream()
                .map(food -> foodMapper.toFoodResponse(food))
                .collect(Collectors.toList());
        return ApiResponse.success(" Food List founded  ",foodList);
    }

    public ApiResponse<List<FoodResponse>> getFoodByCategory(Long categoryId) {
        List<FoodResponse> foodList = foodRepository.findByCategoryId(categoryId)
                .stream()
                .map(food -> foodMapper.toFoodResponse(food))
                .collect(Collectors.toList());

        if (foodList.isEmpty()) {
            return ApiResponse.failure("Food list cannot be found");
        }

        return ApiResponse.success("Food list found", foodList);
    }

    public ApiResponse<List<FoodResponse>> foodWithCategoryAndRestaurant(Long restaurantId, Long categoryId) {
        List<FoodResponse> foodList = foodRepository.findByRestaurantIdAndCategoryId(restaurantId, categoryId)
                .stream()
                .map(food -> foodMapper.toFoodResponse(food))
                .collect(Collectors.toList());

        if (foodList.isEmpty()) {
            return ApiResponse.failure("Food list cannot be found for the given restaurant and category");
        }
        return ApiResponse.success("Food list found", foodList);
    }
}
