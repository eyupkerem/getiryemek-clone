package com.example.getiryemek_clone.service.impl;

import com.example.getiryemek_clone.dto.request.FoodDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.dto.response.FoodResponse;
import com.example.getiryemek_clone.entity.Category;
import com.example.getiryemek_clone.entity.Food;
import com.example.getiryemek_clone.entity.Restaurant;
import com.example.getiryemek_clone.entity.update.FoodUpdateDto;
import com.example.getiryemek_clone.mapper.FoodMapper;
import com.example.getiryemek_clone.repository.CategoryRepository;
import com.example.getiryemek_clone.repository.FoodRepository;
import com.example.getiryemek_clone.repository.RestaurantRepository;
import com.example.getiryemek_clone.service.FoodService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.getiryemek_clone.util.Validations.*;
import static com.example.getiryemek_clone.util.Validations.FOOD_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;
    private final CategoryRepository categoryRepository;
    private final RestaurantRepository restaurantRepository;


  @Cacheable(value = "foods" , key = "'foodList'" , unless = "#result == null")
  public ApiResponse<List<FoodResponse>> getAllFoods() throws InterruptedException {
      Thread.sleep(3000);
      List<FoodResponse> foodList = foodRepository.findAll()
              .stream()
              .map(foodMapper::toFoodResponse)
              .collect(Collectors.toList());

      if (foodList.isEmpty()) {
          return ApiResponse.failure("No foods found");
      }
      return ApiResponse.success(SUCCESS , foodList);
  }


    @Cacheable(value = "food_id", key = "#foodId", unless = "#result == null")
    public ApiResponse<FoodResponse> findFoodById(Long foodId) throws InterruptedException {
        Thread.sleep(3000);

        ApiResponse<FoodResponse> foodResponseApiResponse = foodRepository.findById(foodId)
                .map(food -> ApiResponse.success(SUCCESS, foodMapper.toFoodResponse(food)))
                .orElseGet(() -> ApiResponse.failure(FOOD_NOT_FOUND));

        return foodResponseApiResponse;
    }


    public ApiResponse<List<FoodResponse>> findFoodByName(String foodName) {
        List<FoodResponse> foodList = foodRepository.findByName(foodName)
                .stream()
                .map(food -> foodMapper.toFoodResponse(food))
                .collect(Collectors.toList());

        if (foodList.isEmpty()) {
            return ApiResponse.failure(FOOD_NOT_FOUND);
        }

        return ApiResponse.success(SUCCESS, foodList);
    }


    @CacheEvict(value = {"foods", "resAndCatFood"}, allEntries = true, condition = "#foodDto != null")
    public ApiResponse<FoodResponse>  add(FoodDto foodDto , Long categoryId , Long restaurantId) {
        Category category  =categoryRepository.findById(categoryId).orElseThrow(
                ()->new RuntimeException(CATEGORY_NOT_FOUND));
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                ()->new RuntimeException(RESTAURANT_NOT_FOUND));
        Food newFood = foodMapper.toFood(foodDto);
        newFood.setCategory(category);
        newFood.setRestaurant(restaurant);
        foodRepository.save(newFood);
        return  ApiResponse.success(SUCCESS , foodMapper.toFoodResponse(newFood));
    }

    public ApiResponse<FoodResponse> deleteFood(Long foodId) {
        return foodRepository.findById(foodId)
                .map(food -> {
                    foodRepository.deleteById(foodId);
                    return ApiResponse.success(SUCCESS ,  foodMapper.toFoodResponse(food));
                })
                .orElseGet(()->ApiResponse.failure(ERROR));
    }


    @CacheEvict(value = {"food_id", "foods", "resAndCatFood"}, key = "#foodId")
    public ApiResponse<FoodResponse> update(Long foodId, FoodUpdateDto updateDto) {

        if (updateDto.getName().isBlank() || updateDto.getPrice() ==null){
            return ApiResponse.failure(FIELDS_NOT_EMPTY);
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
                            return ApiResponse.success(SUCCESS,
                                    foodMapper.toFoodResponse(tempFood));
                        }
                )
                .orElseGet(() -> ApiResponse.failure(ERROR));
    }


    public ApiResponse<List<FoodResponse>> getFoodFromRestaurant(Long restaurantId) {
        List<FoodResponse> foodList =  foodRepository.getFoodFromRestaurant(restaurantId)
                .stream()
                .map(food -> foodMapper.toFoodResponse(food))
                .collect(Collectors.toList());
        return ApiResponse.success(SUCCESS,foodList);
    }

    public ApiResponse<List<FoodResponse>> getFoodByCategory(Long categoryId) {
        List<FoodResponse> foodList = foodRepository.findByCategoryId(categoryId)
                .stream()
                .map(food -> foodMapper.toFoodResponse(food))
                .collect(Collectors.toList());

        if (foodList.isEmpty()) {
            return ApiResponse.failure(FOOD_NOT_FOUND);
        }

        return ApiResponse.success(SUCCESS, foodList);
    }

    @Cacheable(value = {"resAndCatFood"} , key = "'catAndResFood'" , unless = "#result == null")
    public ApiResponse<List<FoodResponse>> foodWithCategoryAndRestaurant(Long restaurantId, Long categoryId) {
        List<FoodResponse> foodList = foodRepository.findByRestaurantIdAndCategoryId(restaurantId, categoryId)
                .stream()
                .map(food -> foodMapper.toFoodResponse(food))
                .collect(Collectors.toList());

        if (foodList.isEmpty()) {
            return ApiResponse.failure(FOOD_NOT_FOUND);
        }
        return ApiResponse.success(SUCCESS, foodList);
    }

    }
