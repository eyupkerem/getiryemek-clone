package com.example.getiryemek_clone.service;

import com.example.getiryemek_clone.dto.response.AddressResponse;
import com.example.getiryemek_clone.entity.Address;
import com.example.getiryemek_clone.entity.Costumer;
import com.example.getiryemek_clone.entity.Restaurant;
import com.example.getiryemek_clone.entity.update.AddressUpdateDto;
import com.example.getiryemek_clone.mapper.AddressMapper;
import com.example.getiryemek_clone.repository.AddressRepository;
import com.example.getiryemek_clone.dto.request.AddressDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.repository.CostumerRepository;
import com.example.getiryemek_clone.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final CostumerRepository costumerRepository;
    private final RestaurantRepository restaurantRepository;

    public ApiResponse<List<AddressResponse>> getAllAddress() {
        List<AddressResponse> addressesList = addressRepository.findAll()
                .stream()
                .map(x -> addressMapper.toAddressResponse(x))
                .collect(Collectors.toList());
        return ApiResponse.success("address List founded" ,  addressesList);
    }

    public ApiResponse<AddressResponse> findById(Long addressId) {
        return addressRepository.findById(addressId)
                .map(address ->
                        ApiResponse.success("Address founded succesfully " , addressMapper.toAddressResponse(address)))
                .orElseGet(()->ApiResponse.failure("Address  could not found"));
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
        return ApiResponse.success("Address added", addressMapper.toAddressResponse(newAddress));
    }


    public ApiResponse<AddressResponse> deleteAddress(Long addressId) {
        return addressRepository.findById(addressId)
                .map(address -> {
                    addressRepository.deleteById(addressId);
                    return ApiResponse.success("Address deleted successfully",
                            addressMapper.toAddressResponse(address));
                })
                .orElseGet(() ->
                        ApiResponse.failure("Address could not be found")
                );
    }


    public ApiResponse<AddressResponse> update(Long addressId, AddressUpdateDto updateDto) {

        if (updateDto.getCity() == null || updateDto.getCity().isEmpty() ||
                updateDto.getStreet() == null || updateDto.getStreet().isEmpty() ||
                updateDto.getZipCode() == null || updateDto.getZipCode().isEmpty() ||
                updateDto.getNumber() <0) {
            return ApiResponse.failure("All fields must be non-empty");
        }

        return addressRepository.findById(addressId)
                .map(address -> {
                    address.setStreet(updateDto.getStreet());
                    address.setCity(updateDto.getCity());
                    address.setZipCode(updateDto.getZipCode());
                    return ApiResponse.success("Address updated successfully",
                            addressMapper.toAddressResponse(address));
                })
                .orElseGet(() -> ApiResponse.failure("Address could not updated"));
    }
}
