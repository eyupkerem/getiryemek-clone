package com.example.getiryemek_clone.service.impl;

import com.example.getiryemek_clone.config.PasswordEncoderConfig;
import com.example.getiryemek_clone.dto.request.AuthRequest;
import com.example.getiryemek_clone.dto.request.RestaurantAdminDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.dto.response.RestaurantAdminResponse;
import com.example.getiryemek_clone.entity.Restaurant;
import com.example.getiryemek_clone.entity.RestaurantAdmin;
import com.example.getiryemek_clone.entity.enums.Role;
import com.example.getiryemek_clone.entity.update.RestaurantAdminUpdateDto;
import com.example.getiryemek_clone.mapper.RestaurantAdminMapper;
import com.example.getiryemek_clone.repository.RestaurantAdminRepository;
import com.example.getiryemek_clone.repository.RestaurantRepository;
import com.example.getiryemek_clone.service.JwtService;
import com.example.getiryemek_clone.service.RestaurantAdminService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.getiryemek_clone.util.Validations.*;

@Service
@RequiredArgsConstructor
public class RestaurantAdminServiceImpl implements RestaurantAdminService {
    private final RestaurantAdminRepository restaurantAdminRepository;
    private final RestaurantAdminMapper restaurantAdminMapper;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public ApiResponse<List<RestaurantAdminResponse>> getAllAdmins() {
        List<RestaurantAdminResponse> adminList = restaurantAdminRepository.findAll()
                .stream()
                .map(restaurantAdmin -> restaurantAdminMapper.toRestaurantAdminResponse(restaurantAdmin))
                .collect(Collectors.toList());
        return ApiResponse.success(SUCCESS, adminList);
    }

    public ApiResponse<RestaurantAdminResponse> findById(Long id) {
        return restaurantAdminRepository.findById(id)
                .map(restaurantAdmin -> {
                    return ApiResponse.success(SUCCESS
                            , restaurantAdminMapper.toRestaurantAdminResponse(restaurantAdmin));
                })
                .orElseGet(() -> ApiResponse.failure(RESTAURANTADMIN_NOT_FOUND));

    }


    public ApiResponse<RestaurantAdminResponse> add(RestaurantAdminDto restaurantAdminDto, Long restaurantId) {

        if (!restaurantAdminDto.getEmail().matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {
            return ApiResponse.failure(GMAIL_MANDATORY);
        }
        boolean emailExists = restaurantAdminRepository.findByEmail(restaurantAdminDto.getEmail()).isPresent();
        if(emailExists){
            return ApiResponse.failure(EMAIL_ALREADY_IN_USE);
        }



        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new RuntimeException(RESTAURANT_NOT_FOUND)
        );

        RestaurantAdmin newAdmin = restaurantAdminMapper.toRestaurantAdmin(restaurantAdminDto);
        newAdmin.setRole(Role.RESTAURANT_ADMIN);
        newAdmin.setRestaurant(restaurant);
        String hashedPassword = passwordEncoderConfig.hashPassword(restaurantAdminDto.getPassword());
        newAdmin.setPassword(hashedPassword);
        restaurantAdminRepository.save(newAdmin);
        return ApiResponse.success(SUCCESS
                , restaurantAdminMapper.toRestaurantAdminResponse(newAdmin));
    }

    public ApiResponse<String> generateToken(AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateRestaurantAdminToken(request.email());
                return ApiResponse.success(TOKEN_GENERATED, token);
            } else {
                return ApiResponse.failure(INVALID_CREDENTIALS);
            }
        } catch (Exception e) {
            return ApiResponse.failure(AUTHENTICATION_FAILED);
        }
    }

    public ApiResponse<RestaurantAdminResponse> update(Long id, RestaurantAdminUpdateDto updateDto) {

        if (updateDto.getName() == null ||
                updateDto.getSurname() == null ||
                updateDto.getPassword() == null) {
            return ApiResponse.failure(FIELDS_NOT_EMPTY);
        }
        return restaurantAdminRepository.findById(id)
                .map(restaurantAdmin -> {
                    restaurantAdmin.setName(updateDto.getName());
                    restaurantAdmin.setSurname(updateDto.getSurname());
                    restaurantAdmin.setPassword(updateDto.getPassword());
                    restaurantAdminRepository.save(restaurantAdmin);
                    return ApiResponse.success(SUCCESS
                            , restaurantAdminMapper.toRestaurantAdminResponse(restaurantAdmin));
                })
                .orElseGet(() -> ApiResponse.failure(ERROR));
    }

    public ApiResponse<RestaurantAdminResponse> deleteRestaurantAdmin(Long id) {
        return restaurantAdminRepository.findById(id)
                .map(restaurantAdmin -> {
                    restaurantAdminRepository.deleteById(id);
                    return ApiResponse.success(SUCCESS
                            , restaurantAdminMapper.toRestaurantAdminResponse(restaurantAdmin));
                })
                .orElseGet(() -> ApiResponse.failure(ERROR));
    }
}
