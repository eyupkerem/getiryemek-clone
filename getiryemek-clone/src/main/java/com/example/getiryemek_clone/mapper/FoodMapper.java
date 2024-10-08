package com.example.getiryemek_clone.mapper;

import com.example.getiryemek_clone.dto.request.FoodDto;
import com.example.getiryemek_clone.dto.response.FoodResponse;
import com.example.getiryemek_clone.entity.Food;
import org.mapstruct.Mapper;



@Mapper(componentModel = "spring")
public interface FoodMapper {
    Food toFood(FoodDto foodDto);
    FoodDto toFoodTo(Food food);
    FoodResponse toFoodResponse(Food food);
}
