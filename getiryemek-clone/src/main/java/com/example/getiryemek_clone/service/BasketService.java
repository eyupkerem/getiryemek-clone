package com.example.getiryemek_clone.service;

import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.dto.response.FoodResponse;
import com.example.getiryemek_clone.entity.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BasketService {
    ApiResponse<List<Basket>> getAllBaskets();

    ApiResponse<Basket> getById(Long basketId);

    ApiResponse<FoodResponse> addItemToBasket(HttpServletRequest httpServletRequest, Long foodId);

    ApiResponse<FoodResponse> deleteItemFromBasket(HttpServletRequest httpServletRequest, Long foodId);
}
