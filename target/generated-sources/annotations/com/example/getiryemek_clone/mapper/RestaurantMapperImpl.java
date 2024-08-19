package com.example.getiryemek_clone.mapper;

import com.example.getiryemek_clone.dto.request.RestaurantDto;
import com.example.getiryemek_clone.dto.response.RestaurantResponse;
import com.example.getiryemek_clone.entity.Restaurant;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-19T10:00:59+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class RestaurantMapperImpl implements RestaurantMapper {

    @Override
    public Restaurant toRestaurant(RestaurantDto restaurantDto) {
        if ( restaurantDto == null ) {
            return null;
        }

        Restaurant restaurant = new Restaurant();

        return restaurant;
    }

    @Override
    public RestaurantDto toRestaurantDto(Restaurant restaurant) {
        if ( restaurant == null ) {
            return null;
        }

        RestaurantDto restaurantDto = new RestaurantDto();

        return restaurantDto;
    }

    @Override
    public RestaurantResponse toRestaurantResponse(Restaurant restaurant) {
        if ( restaurant == null ) {
            return null;
        }

        RestaurantResponse restaurantResponse = new RestaurantResponse();

        return restaurantResponse;
    }
}
