package com.example.getiryemek_clone.mapper;

import com.example.getiryemek_clone.dto.request.RestaurantAdminDto;
import com.example.getiryemek_clone.dto.response.RestaurantAdminResponse;
import com.example.getiryemek_clone.entity.RestaurantAdmin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestaurantAdminMapper {
    RestaurantAdmin toRestaurantAdmin(RestaurantAdminDto restaurantAdminDto);
    RestaurantAdminDto toRestaurantAdminDto(RestaurantAdmin restaurantAdmin);
    RestaurantAdminResponse toRestaurantAdminResponse(RestaurantAdmin restaurantAdmin);

}
