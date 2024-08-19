package com.example.getiryemek_clone.service;

import com.example.getiryemek_clone.entity.Restaurant;
import com.example.getiryemek_clone.entity.RestaurantAdmin;
import com.example.getiryemek_clone.entity.enums.Role;
import com.example.getiryemek_clone.entity.update.RestaurantAdminUpdateDto;
import com.example.getiryemek_clone.mapper.RestaurantAdminMapper;
import com.example.getiryemek_clone.repository.RestaurantAdminRepository;
import com.example.getiryemek_clone.dto.request.RestaurantAdminDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantAdminService {
    private final RestaurantAdminRepository restaurantAdminRepository;
    private final RestaurantAdminMapper restaurantAdminMapper;
    private final RestaurantRepository restaurantRepository;


    public ApiResponse<List<RestaurantAdmin>> getAllAdmins() {
        List<RestaurantAdmin> adminList = restaurantAdminRepository.findAll();
        return ApiResponse.success("restaurant list founded" , adminList);
    }

    public ApiResponse<RestaurantAdmin> findById(Long id){


        return restaurantAdminRepository.findById(id)
                .map(restaurantAdmin ->{
                    return ApiResponse.success("Restaurant Admin founded" , restaurantAdmin);
                })
                .orElseGet(()->ApiResponse.failure("Restaurant admin could not founded"));

    }


    public ApiResponse<RestaurantAdmin> add(RestaurantAdminDto restaurantAdminDto , Long restaurantId) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                ()-> new RuntimeException("Restaurant could not found")
        );

        RestaurantAdmin newAdmin =  restaurantAdminMapper.toRestaurantAdmin(restaurantAdminDto);
        newAdmin.setRole(Role.RESTAURANT_ADMIN);
        newAdmin.setRestaurant(restaurant);
        restaurantAdminRepository.save(newAdmin);
        return ApiResponse.success("Admin added succesfully" , newAdmin );
    }

    public ApiResponse<RestaurantAdmin> update(Long id, RestaurantAdminUpdateDto updateDto) {

        if (updateDto.getName() == null ||
                updateDto.getSurname() == null ||
                updateDto.getPassword() == null ){
        return ApiResponse.failure("All fields must be non-empty");
        }
        return restaurantAdminRepository.findById(id)
                .map(restaurantAdmin -> {
                    restaurantAdmin.setName(updateDto.getName());
                    restaurantAdmin.setSurname(updateDto.getSurname());
                    restaurantAdmin.setPassword(updateDto.getPassword());
                    restaurantAdminRepository.save(restaurantAdmin);
                    return ApiResponse.success("Restaurant admin update succesfıully" , restaurantAdmin);
                })
                .orElseGet(()->ApiResponse.failure("Restuarant admin could not update"));
    }

    public ApiResponse<RestaurantAdmin> deleteRestaurantAdmin(Long id){
        return restaurantAdminRepository.findById(id)
                .map(restaurantAdmin -> {
                    restaurantAdminRepository.deleteById(id);
                    return ApiResponse.success("Restaurant Admin deleted succersfully" , restaurantAdmin);
                })
                .orElseGet(()->ApiResponse.failure("REstaurant admin could not deleted"));
    }


}
