package com.example.getiryemek_clone.service;

import com.example.getiryemek_clone.config.PasswordEncoderConfig;
import com.example.getiryemek_clone.dto.request.AuthRequest;
import com.example.getiryemek_clone.dto.response.RestaurantAdminResponse;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantAdminService {
    private final RestaurantAdminRepository restaurantAdminRepository;
    private final RestaurantAdminMapper restaurantAdminMapper;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;

    public ApiResponse<List<RestaurantAdminResponse>> getAllAdmins() {
        List<RestaurantAdminResponse> adminList = restaurantAdminRepository.findAll()
                .stream()
                .map(restaurantAdmin -> restaurantAdminMapper.toRestaurantAdminResponse(restaurantAdmin))
                .collect(Collectors.toList());
        return ApiResponse.success("restaurant list founded", adminList);
    }

    public ApiResponse<RestaurantAdminResponse> findById(Long id) {
        return restaurantAdminRepository.findById(id)
                .map(restaurantAdmin -> {
                    return ApiResponse.success("Restaurant Admin founded"
                            , restaurantAdminMapper.toRestaurantAdminResponse(restaurantAdmin));
                })
                .orElseGet(() -> ApiResponse.failure("Restaurant admin could not founded"));

    }


    public ApiResponse<RestaurantAdminResponse> add(RestaurantAdminDto restaurantAdminDto, Long restaurantId) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new RuntimeException("Restaurant could not found")
        );

        RestaurantAdmin newAdmin = restaurantAdminMapper.toRestaurantAdmin(restaurantAdminDto);
        newAdmin.setRole(Role.RESTAURANT_ADMIN);
        newAdmin.setRestaurant(restaurant);
        String hashedPassword = passwordEncoderConfig.hashPassword(restaurantAdminDto.getPassword());
        newAdmin.setPassword(hashedPassword);
        restaurantAdminRepository.save(newAdmin);
        return ApiResponse.success("Admin added succesfully"
                , restaurantAdminMapper.toRestaurantAdminResponse(newAdmin));
    }


    public ApiResponse<RestaurantAdminResponse> update(Long id, RestaurantAdminUpdateDto updateDto) {

        if (updateDto.getName() == null ||
                updateDto.getSurname() == null ||
                updateDto.getPassword() == null) {
            return ApiResponse.failure("All fields must be non-empty");
        }
        return restaurantAdminRepository.findById(id)
                .map(restaurantAdmin -> {
                    restaurantAdmin.setName(updateDto.getName());
                    restaurantAdmin.setSurname(updateDto.getSurname());
                    restaurantAdmin.setPassword(updateDto.getPassword());
                    restaurantAdminRepository.save(restaurantAdmin);
                    return ApiResponse.success("Restaurant admin update succesfıully"
                            , restaurantAdminMapper.toRestaurantAdminResponse(restaurantAdmin));
                })
                .orElseGet(() -> ApiResponse.failure("Restuarant admin could not update"));
    }

    public ApiResponse<RestaurantAdminResponse> deleteRestaurantAdmin(Long id) {
        return restaurantAdminRepository.findById(id)
                .map(restaurantAdmin -> {
                    restaurantAdminRepository.deleteById(id);
                    return ApiResponse.success("Restaurant Admin deleted succersfully"
                            , restaurantAdminMapper.toRestaurantAdminResponse(restaurantAdmin));
                })
                .orElseGet(() -> ApiResponse.failure("REstaurant admin could not deleted"));
    }
}