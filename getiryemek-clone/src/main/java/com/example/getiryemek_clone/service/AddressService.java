package com.example.getiryemek_clone.service;

import com.example.getiryemek_clone.dto.response.AddressResponse;
import com.example.getiryemek_clone.entity.Address;
import com.example.getiryemek_clone.entity.Costumer;
import com.example.getiryemek_clone.entity.Restaurant;
import com.example.getiryemek_clone.entity.update.AddressUpdateDto;
import com.example.getiryemek_clone.mapper.AddressMapper;
import com.example.getiryemek_clone.mapper.CostumerMapper;
import com.example.getiryemek_clone.repository.AddressRepository;
import com.example.getiryemek_clone.dto.request.AddressDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
  //  private final CostumerMapper costumerMapper;
    private final AddressMapper addressMapper;
    private final CostumerService costumerService;
    private final RestaurantService restaurantService;

    public ApiResponse<List<Address>> getAllAddress() {
        List<Address> addressesList = addressRepository.findAll()
                .stream().collect(Collectors.toList());
        return ApiResponse.success("address List founded" , addressesList);
    }

    public ApiResponse<Address> findById(Long addressId) {

        return addressRepository.findById(addressId)
                .map(address ->
                        ApiResponse.success("Address founded succesfully " , address))
                .orElseGet(()->ApiResponse.failure("Address  could not found"));
    }



    public ApiResponse<AddressResponse> add(Long costumerId, Long restaurantId, AddressDto addressDto) {
        Address newAddress = addressMapper.toAddress(addressDto);

        if (costumerId != null) {
            Costumer costumer = costumerService.findById(costumerId).getData();
            if (costumer == null) {
                return ApiResponse.failure("Costumer not found");
            }
            newAddress.setCostumer(costumer);
        } else if (restaurantId != null) {
            Restaurant restaurant = restaurantService.findById(restaurantId).getData();
            if (restaurant == null) {
                return ApiResponse.failure("Restaurant not found");
            }
            newAddress.setRestaurant(restaurant);
        } else {
            return ApiResponse.failure("Either costumerId or restaurantId must be provided");
        }

        addressRepository.save(newAddress);
        return ApiResponse.success("Address added", addressMapper.toAddressResponse(newAddress));
    }


    public ApiResponse<Address> deleteAddress(Long addressId) {

        return addressRepository.findById(addressId)
                .map(address -> {
                    addressRepository.deleteById(addressId);
                    return ApiResponse.success("Address deleted successfully", address);
                })
                .orElseGet(() ->
                        ApiResponse.failure("Address could not be found")
                );
    }


    public ApiResponse<Address> update(Long addressId, AddressUpdateDto updateDto) {

        if (updateDto.getCity().isEmpty() ||
                updateDto.getStreet().isEmpty() ||
                updateDto.getZipCode().isEmpty()
        ){
            return ApiResponse.failure("All fields must be non-empty");
        }

        return addressRepository.findById(addressId)
                .map(address -> {
                    address.setStreet(updateDto.getStreet());
                    address.setCity(updateDto.getCity());
                    address.setZipCode(updateDto.getZipCode());
                    return ApiResponse.success("Address updated succesfully", address);
                })
                .orElseGet(() -> ApiResponse.failure("Addres could not updated"));

    }

}
