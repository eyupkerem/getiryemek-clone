package com.example.getiryemek_clone.mapper;

import com.example.getiryemek_clone.dto.request.RestaurantDto;
import com.example.getiryemek_clone.dto.response.RestaurantResponse;
import com.example.getiryemek_clone.entity.Food;
import com.example.getiryemek_clone.entity.Restaurant;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-09T13:28:24+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class RestaurantMapperImpl implements RestaurantMapper {

    @Override
    public Restaurant toRestaurant(RestaurantDto restaurantDto) {
        if ( restaurantDto == null ) {
            return null;
        }

        Restaurant restaurant = new Restaurant();

        restaurant.setName( restaurantDto.getName() );
        restaurant.setPhoneNumber( restaurantDto.getPhoneNumber() );

        return restaurant;
    }

    @Override
    public RestaurantDto toRestaurantDto(Restaurant restaurant) {
        if ( restaurant == null ) {
            return null;
        }

        RestaurantDto.RestaurantDtoBuilder restaurantDto = RestaurantDto.builder();

        restaurantDto.name( restaurant.getName() );
        restaurantDto.phoneNumber( restaurant.getPhoneNumber() );

        return restaurantDto.build();
    }

    @Override
    public RestaurantResponse toRestaurantResponse(Restaurant restaurant) {
        if ( restaurant == null ) {
            return null;
        }

        RestaurantResponse restaurantResponse = new RestaurantResponse();

        restaurantResponse.setName( restaurant.getName() );
        restaurantResponse.setAddress( restaurant.getAddress() );
        restaurantResponse.setPhoneNumber( restaurant.getPhoneNumber() );
        List<Food> list = restaurant.getFoods();
        if ( list != null ) {
            restaurantResponse.setFoods( new ArrayList<Food>( list ) );
        }

        return restaurantResponse;
    }
}
