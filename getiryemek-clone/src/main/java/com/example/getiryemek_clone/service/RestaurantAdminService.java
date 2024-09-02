package com.example.getiryemek_clone.service;

import com.example.getiryemek_clone.dto.request.AuthRequest;
import com.example.getiryemek_clone.dto.request.RestaurantAdminDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.dto.response.RestaurantAdminResponse;
import com.example.getiryemek_clone.entity.update.RestaurantAdminUpdateDto;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface RestaurantAdminService {

    ApiResponse<String> generateToken(AuthRequest request);

    ApiResponse<RestaurantAdminResponse> findById(Long restaurantAdminId);

    ApiResponse<RestaurantAdminResponse> add(RestaurantAdminDto restaurantAdminDto, Long restaurantId);

    ApiResponse<RestaurantAdminResponse> update(Long id, RestaurantAdminUpdateDto updateDto);

    ApiResponse<RestaurantAdminResponse> deleteRestaurantAdmin(Long id);

    ApiResponse<List<RestaurantAdminResponse>> getAllAdmins();
}