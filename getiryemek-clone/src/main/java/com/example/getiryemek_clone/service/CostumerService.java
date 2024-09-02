package com.example.getiryemek_clone.service;

import com.example.getiryemek_clone.dto.request.AuthRequest;
import com.example.getiryemek_clone.dto.response.CostumerResponse;
import com.example.getiryemek_clone.entity.update.CostumerUpdateDto;
import com.example.getiryemek_clone.dto.request.CostumerDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CostumerService {
    ApiResponse<List<CostumerResponse>> getAllCostumers();
    ApiResponse<CostumerResponse> findById(Long id);

    ApiResponse<String> generateToken(AuthRequest request);

    ApiResponse<CostumerResponse> findByPhoneNumber(String phoneNumber);

    ApiResponse<CostumerResponse> findByEmail(String email);

    ApiResponse<CostumerResponse> update(Long costumerId, CostumerUpdateDto updateDto);

    ApiResponse<CostumerResponse> add(CostumerDto costumerDto);

    ApiResponse<CostumerResponse> deleteCostumer(Long costumerId);

}



