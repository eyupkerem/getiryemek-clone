package com.example.getiryemek_clone.mapper;

import com.example.getiryemek_clone.dto.request.FoodDto;
import com.example.getiryemek_clone.dto.response.FoodResponse;
import com.example.getiryemek_clone.entity.Food;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-06T11:36:50+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class FoodMapperImpl implements FoodMapper {

    @Override
    public Food toFood(FoodDto foodDto) {
        if ( foodDto == null ) {
            return null;
        }

        Food.FoodBuilder food = Food.builder();

        food.name( foodDto.getName() );
        food.price( foodDto.getPrice() );
        food.imageUrl( foodDto.getImageUrl() );
        food.ingredients( foodDto.getIngredients() );

        return food.build();
    }

    @Override
    public FoodDto toFoodTo(Food food) {
        if ( food == null ) {
            return null;
        }

        FoodDto.FoodDtoBuilder foodDto = FoodDto.builder();

        foodDto.name( food.getName() );
        foodDto.price( food.getPrice() );
        foodDto.imageUrl( food.getImageUrl() );
        foodDto.ingredients( food.getIngredients() );

        return foodDto.build();
    }

    @Override
    public FoodResponse toFoodResponse(Food food) {
        if ( food == null ) {
            return null;
        }

        FoodResponse.FoodResponseBuilder foodResponse = FoodResponse.builder();

        foodResponse.name( food.getName() );
        foodResponse.price( food.getPrice() );
        foodResponse.ingredients( food.getIngredients() );
        foodResponse.category( food.getCategory() );
        foodResponse.restaurant( food.getRestaurant() );

        return foodResponse.build();
    }
}
