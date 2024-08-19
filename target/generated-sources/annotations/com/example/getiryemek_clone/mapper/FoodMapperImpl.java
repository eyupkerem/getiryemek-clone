package com.example.getiryemek_clone.mapper;

import com.example.getiryemek_clone.dto.request.FoodDto;
import com.example.getiryemek_clone.dto.response.FoodResponse;
import com.example.getiryemek_clone.entity.Food;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-19T10:00:59+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class FoodMapperImpl implements FoodMapper {

    @Override
    public Food toFood(FoodDto foodDto) {
        if ( foodDto == null ) {
            return null;
        }

        Food food = new Food();

        return food;
    }

    @Override
    public FoodDto toFoodTo(Food food) {
        if ( food == null ) {
            return null;
        }

        FoodDto foodDto = new FoodDto();

        return foodDto;
    }

    @Override
    public FoodResponse toFoodResponse(Food food) {
        if ( food == null ) {
            return null;
        }

        FoodResponse foodResponse = new FoodResponse();

        return foodResponse;
    }
}
