package com.example.getiryemek_clone.service.impl;

import com.example.getiryemek_clone.dto.request.AddressDto;
import com.example.getiryemek_clone.dto.response.AddressResponse;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.entity.Address;
import com.example.getiryemek_clone.entity.Costumer;
import com.example.getiryemek_clone.entity.Restaurant;
import com.example.getiryemek_clone.entity.update.AddressUpdateDto;
import com.example.getiryemek_clone.mapper.AddressMapper;
import com.example.getiryemek_clone.repository.AddressRepository;
import com.example.getiryemek_clone.repository.CostumerRepository;
import com.example.getiryemek_clone.repository.RestaurantRepository;
import com.example.getiryemek_clone.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.getiryemek_clone.util.Validations.*;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final CostumerRepository costumerRepository;
    private final RestaurantRepository restaurantRepository;

    public ApiResponse<List<AddressResponse>> getAllAddress() {
        List<AddressResponse> addressesList = addressRepository.findAll()
                .stream()
                .map(address -> addressMapper.toAddressResponse(address))
                .collect(Collectors.toList());
        return ApiResponse.success(SUCCESS ,  addressesList);
    }

    public ApiResponse<AddressResponse> findById(Long addressId) {
        return addressRepository.findById(addressId)
                .map(address ->
                        ApiResponse.success(SUCCESS , addressMapper.toAddressResponse(address)))
                .orElseGet(()->ApiResponse.failure(ADDRESS_NOT_FOUND));
    }

    public ApiResponse<AddressResponse> add(Long costumerId, Long restaurantId, AddressDto addressDto) {
        Address newAddress = addressMapper.toAddress(addressDto);

        if (costumerId != null) {
            Costumer costumer = costumerRepository.findById(costumerId).get();
            newAddress.setCostumer(costumer);
        } else if (restaurantId != null) {
            Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
            newAddress.setRestaurant(restaurant);
        } else {
            return ApiResponse.failure("Either costumerId or restaurantId must be provided");
        }
        addressRepository.save(newAddress);
        return ApiResponse.success(SUCCESS, addressMapper.toAddressResponse(newAddress));
    }
    public ApiResponse<AddressResponse> deleteAddress(Long addressId) {
        return addressRepository.findById(addressId)
                .map(address -> {
                    addressRepository.deleteById(addressId);
                    return ApiResponse.success(SUCCESS,
                            addressMapper.toAddressResponse(address));
                })
                .orElseGet(() ->
                        ApiResponse.failure(ERROR)
                );
    }

    public ApiResponse<AddressResponse> update(Long addressId, AddressUpdateDto updateDto) {

        if (updateDto.getCity().isBlank() ||
                updateDto.getStreet().isBlank() ||
                updateDto.getZipCode().isBlank() ||
                updateDto.getNumber()<0
        ){
            return ApiResponse.failure(FIELDS_NOT_EMPTY);
        }

        return addressRepository.findById(addressId)
                .map(address -> {
                    address.setStreet(updateDto.getStreet());
                    address.setCity(updateDto.getCity());
                    address.setZipCode(updateDto.getZipCode());
                    return ApiResponse.success(SUCCESS,
                            addressMapper.toAddressResponse(address));
                })
                .orElseGet(() -> ApiResponse.failure(ERROR ));
    }
}
