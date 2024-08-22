package com.example.getiryemek_clone.service;

import com.example.getiryemek_clone.dto.response.RestaurantResponse;
import com.example.getiryemek_clone.entity.Address;
import com.example.getiryemek_clone.entity.Food;
import com.example.getiryemek_clone.entity.Restaurant;
import com.example.getiryemek_clone.entity.update.RestaurantUpdateDto;
import com.example.getiryemek_clone.mapper.RestaurantMapper;
import com.example.getiryemek_clone.repository.AddressRepository;
import com.example.getiryemek_clone.repository.RestaurantRepository;
import com.example.getiryemek_clone.dto.request.RestaurantDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final AddressRepository addressRepository;
    private final RestaurantMapper restaurantMapper;

    public ApiResponse<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        return ApiResponse.success("Restaurant list founded " , restaurantList);
    }

    public ApiResponse<Restaurant> findById(Long restaurantId) {

        return restaurantRepository.findById(restaurantId)
                .map(restaurant -> ApiResponse.success("Restaurant founded" , restaurant))
                .orElseGet(()-> ApiResponse.failure("Restaurant could not found"));

    }

    public ApiResponse<Restaurant> findByName(String restaurantName) {
        return restaurantRepository.findByName(restaurantName)
                .map(restaurant -> ApiResponse.success("Restaurant founded" , restaurant))
                .orElseGet(()-> ApiResponse.failure("Restaurant could not found"));
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
        return ApiResponse.success("Restaurant added succesfully" , restaurantMapper.toRestaurantResponse(newRestaurant));
    }

    public ApiResponse<Restaurant> update(Long restaurantId, RestaurantUpdateDto updateDto , Long addressId) {

        if (updateDto.getName() == null || updateDto.getName().isEmpty() ||
                updateDto.getPhoneNumber() == null || updateDto.getPhoneNumber().isEmpty()) {

            return ApiResponse.failure("All fields must be non-empty");
        }
        Address address = addressRepository.findById(addressId).orElseThrow(
                ()-> new RuntimeException("Address could not found !")
        );

        return restaurantRepository.findById(restaurantId)
                .map(restaurant -> {
                    restaurant.setName(updateDto.getName());
                    restaurant.setPhoneNumber(updateDto.getPhoneNumber());
                    restaurant.setAddress(address);
                    restaurantRepository.save(restaurant);
                    return ApiResponse.success("Restaurant updated successfully", restaurant);
                })
                .orElseGet(() -> ApiResponse.failure("Restaurant not found"));
    }


    public ApiResponse<Restaurant> deleteRestaurant(Long id) {
        return restaurantRepository.findById(id)
                .map(restaurant -> {
                    restaurantRepository.deleteById(id);
                    return ApiResponse.success("Restaurant deleted sucessfully" , restaurant);
                })
                .orElseGet(()->ApiResponse.failure("Restaıurant could not deleted"));
    }

}
