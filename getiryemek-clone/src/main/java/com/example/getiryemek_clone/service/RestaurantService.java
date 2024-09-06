package com.example.getiryemek_clone.service;

import com.example.getiryemek_clone.dto.request.RestaurantDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.dto.response.RestaurantResponse;
import com.example.getiryemek_clone.entity.update.RestaurantUpdateDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestaurantService {

    ApiResponse<List<RestaurantResponse>> getAllRestaurants() throws InterruptedException;

    ApiResponse<RestaurantResponse> findById(Long restaurantId);

    ApiResponse<RestaurantResponse> findByName(String restaurantName);

    ApiResponse<RestaurantResponse> add(Long addressId, RestaurantDto restaurantDto);

    ApiResponse<RestaurantResponse> update(HttpServletRequest httpServletRequest, RestaurantUpdateDto updateDto, Long addressId);

    ApiResponse<RestaurantResponse> deleteRestaurant(Long id);
}
