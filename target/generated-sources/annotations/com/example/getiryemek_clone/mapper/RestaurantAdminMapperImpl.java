package com.example.getiryemek_clone.mapper;

import com.example.getiryemek_clone.dto.request.RestaurantAdminDto;
import com.example.getiryemek_clone.dto.response.RestaurantAdminResponse;
import com.example.getiryemek_clone.entity.RestaurantAdmin;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-03T16:51:43+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class RestaurantAdminMapperImpl implements RestaurantAdminMapper {

    @Override
    public RestaurantAdmin toRestaurantAdmin(RestaurantAdminDto restaurantAdminDto) {
        if ( restaurantAdminDto == null ) {
            return null;
        }

        RestaurantAdmin restaurantAdmin = new RestaurantAdmin();

        restaurantAdmin.setName( restaurantAdminDto.getName() );
        restaurantAdmin.setSurname( restaurantAdminDto.getSurname() );
        restaurantAdmin.setPassword( restaurantAdminDto.getPassword() );
        restaurantAdmin.setEmail( restaurantAdminDto.getEmail() );

        return restaurantAdmin;
    }

    @Override
    public RestaurantAdminDto toRestaurantAdminDto(RestaurantAdmin restaurantAdmin) {
        if ( restaurantAdmin == null ) {
            return null;
        }

        RestaurantAdminDto.RestaurantAdminDtoBuilder restaurantAdminDto = RestaurantAdminDto.builder();

        restaurantAdminDto.name( restaurantAdmin.getName() );
        restaurantAdminDto.surname( restaurantAdmin.getSurname() );
        restaurantAdminDto.password( restaurantAdmin.getPassword() );
        restaurantAdminDto.email( restaurantAdmin.getEmail() );

        return restaurantAdminDto.build();
    }

    @Override
    public RestaurantAdminResponse toRestaurantAdminResponse(RestaurantAdmin restaurantAdmin) {
        if ( restaurantAdmin == null ) {
            return null;
        }

        RestaurantAdminResponse restaurantAdminResponse = new RestaurantAdminResponse();

        restaurantAdminResponse.setName( restaurantAdmin.getName() );
        restaurantAdminResponse.setSurname( restaurantAdmin.getSurname() );
        restaurantAdminResponse.setEmail( restaurantAdmin.getEmail() );
        restaurantAdminResponse.setRestaurant( restaurantAdmin.getRestaurant() );

        return restaurantAdminResponse;
    }
}
