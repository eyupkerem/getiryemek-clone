package com.example.getiryemek_clone.service;

import com.example.getiryemek_clone.dto.response.AddressResponse;
import com.example.getiryemek_clone.entity.update.AddressUpdateDto;
import com.example.getiryemek_clone.dto.request.AddressDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {

    ApiResponse<List<AddressResponse>> getAllAddress();

    ApiResponse<AddressResponse> findById(Long addressId);

    ApiResponse<AddressResponse> add(Long costumerId, Long restaurantId, AddressDto addressDto);

    ApiResponse<AddressResponse> update(Long addressId, AddressUpdateDto updateDto);

    ApiResponse<AddressResponse> deleteAddress(Long addressId);
}
