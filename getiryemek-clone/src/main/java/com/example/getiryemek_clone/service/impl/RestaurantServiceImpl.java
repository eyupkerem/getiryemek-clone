package com.example.getiryemek_clone.service.impl;

import com.example.getiryemek_clone.dto.request.RestaurantDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.dto.response.RestaurantResponse;
import com.example.getiryemek_clone.entity.Address;
import com.example.getiryemek_clone.entity.Restaurant;
import com.example.getiryemek_clone.entity.update.RestaurantUpdateDto;
import com.example.getiryemek_clone.mapper.RestaurantMapper;
import com.example.getiryemek_clone.repository.AddressRepository;
import com.example.getiryemek_clone.repository.RestaurantRepository;
import com.example.getiryemek_clone.service.JwtService;
import com.example.getiryemek_clone.service.RestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.getiryemek_clone.util.Validations.*;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final AddressRepository addressRepository;
    private final RestaurantMapper restaurantMapper;
    private final JwtService jwtService;

    public ApiResponse<List<RestaurantResponse>> getAllRestaurants() {
        List<RestaurantResponse> restaurantList = restaurantRepository.findAll()
                .stream()
                .map(restaurant -> restaurantMapper.toRestaurantResponse(restaurant))
                .collect(Collectors.toList());
        return ApiResponse.success(SUCCESS , restaurantList);
    }

    public ApiResponse<RestaurantResponse> findById(Long restaurantId) {

        return restaurantRepository.findById(restaurantId)
                .map(restaurant -> ApiResponse.success(SUCCESS
                        , restaurantMapper.toRestaurantResponse(restaurant) ))
                .orElseGet(()-> ApiResponse.failure(RESTAURANT_NOT_FOUND));

    }

    public ApiResponse<RestaurantResponse> findByName(String restaurantName) {
        return restaurantRepository.findByName(restaurantName)
                .map(restaurant -> ApiResponse.success(SUCCESS
                        , restaurantMapper.toRestaurantResponse(restaurant) ))
                .orElseGet(()-> ApiResponse.failure(RESTAURANT_NOT_FOUND));
    }

    public ApiResponse<RestaurantResponse> add(Long addressId , RestaurantDto restaurantDto) {
        Restaurant newRestaurant =  restaurantMapper.toRestaurant(restaurantDto);

        if (addressId != null) {
            Address address = addressRepository.findById(addressId).orElse(null);
            newRestaurant.setAddress(address);
        } else {
            newRestaurant.setAddress(null);
        }
        restaurantRepository.save(newRestaurant);
        return ApiResponse.success(SUCCESS , restaurantMapper.toRestaurantResponse(newRestaurant));
    }

    public ApiResponse<RestaurantResponse> update(HttpServletRequest httpServletRequest
            , RestaurantUpdateDto updateDto
            , Long addressId) {

        String token = httpServletRequest.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        } else {
            throw new RuntimeException("JWT token is missing or invalid");
        }

        Long id= jwtService.extractId(token);

        if (updateDto.getName().isBlank() ||
                updateDto.getPhoneNumber().isBlank()
        ){
            return ApiResponse.failure(FIELDS_NOT_EMPTY);
        }

        Address address = addressRepository.findById(addressId).orElseThrow(
                ()-> new RuntimeException(ADDRESS_NOT_FOUND)
        );

        return restaurantRepository.findById(id)
                .map(restaurant -> {
                    restaurant.setName(updateDto.getName());
                    restaurant.setPhoneNumber(updateDto.getPhoneNumber());
                    restaurant.setAddress(address);
                    restaurantRepository.save(restaurant);
                    return ApiResponse.success(SUCCESS
                            , restaurantMapper.toRestaurantResponse(restaurant) );
                })
                .orElseGet(() -> ApiResponse.failure(RESTAURANT_NOT_FOUND));
    }


    public ApiResponse<RestaurantResponse> deleteRestaurant(Long id) {
        return restaurantRepository.findById(id)
                .map(restaurant -> {
                    restaurantRepository.deleteById(id);
                    return ApiResponse.success(SUCCESS
                            , restaurantMapper.toRestaurantResponse(restaurant) );
                })
                .orElseGet(()->ApiResponse.failure(ERROR));
    }
}
