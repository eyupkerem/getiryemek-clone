package com.example.getiryemek_clone.mapper;

import com.example.getiryemek_clone.dto.request.RestaurantDto;
import com.example.getiryemek_clone.dto.response.RestaurantResponse;
import com.example.getiryemek_clone.entity.Restaurant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface RestaurantMapper {
    Restaurant toRestaurant(RestaurantDto restaurantDto);
    RestaurantDto toRestaurantDto(Restaurant restaurant);
    RestaurantResponse toRestaurantResponse(Restaurant restaurant);
}
