package com.example.getiryemek_clone.mapper;

import com.example.getiryemek_clone.dto.request.RestaurantAdminDto;
import com.example.getiryemek_clone.dto.response.RestaurantAdminResponse;
import com.example.getiryemek_clone.entity.RestaurantAdmin;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-19T10:00:59+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class RestaurantAdminMapperImpl implements RestaurantAdminMapper {

    @Override
    public RestaurantAdmin toRestaurantAdmin(RestaurantAdminDto restaurantAdminDto) {
        if ( restaurantAdminDto == null ) {
            return null;
        }

        RestaurantAdmin restaurantAdmin = new RestaurantAdmin();

        return restaurantAdmin;
    }

    @Override
    public RestaurantAdminDto toRestaurantAdminDto(RestaurantAdmin restaurantAdmin) {
        if ( restaurantAdmin == null ) {
            return null;
        }

        RestaurantAdminDto restaurantAdminDto = new RestaurantAdminDto();

        return restaurantAdminDto;
    }

    @Override
    public RestaurantAdminResponse toRestaurantAdminResponse(RestaurantAdmin restaurantAdmin) {
        if ( restaurantAdmin == null ) {
            return null;
        }

        RestaurantAdminResponse restaurantAdminResponse = new RestaurantAdminResponse();

        return restaurantAdminResponse;
    }
}
