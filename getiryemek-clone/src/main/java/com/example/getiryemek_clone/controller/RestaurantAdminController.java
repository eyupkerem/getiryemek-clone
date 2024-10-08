package com.example.getiryemek_clone.controller;

import com.example.getiryemek_clone.dto.request.AuthRequest;
import com.example.getiryemek_clone.dto.response.RestaurantAdminResponse;
import com.example.getiryemek_clone.dto.request.RestaurantAdminDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.entity.update.RestaurantAdminUpdateDto;
import com.example.getiryemek_clone.service.JwtService;
import com.example.getiryemek_clone.service.RestaurantAdminService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@SecurityRequirement(name = "BearerAuth")
@RequestMapping("/api/restaurant-admin")
public class RestaurantAdminController {

    private final RestaurantAdminService restaurantAdminService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse> getAllRestaurantAdmin(){
        ApiResponse<List<RestaurantAdminResponse>> response = restaurantAdminService.getAllAdmins();
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{restaurantAdminId}")
    public ResponseEntity<ApiResponse> getRestaurantAdminById(@PathVariable Long restaurantAdminId){
        ApiResponse<RestaurantAdminResponse> response=restaurantAdminService.findById(restaurantAdminId);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{restaurantId}")
    public ResponseEntity<ApiResponse> addRestaurantAdmin(@RequestBody RestaurantAdminDto restaurantAdminDto ,
                                                          @PathVariable Long restaurantId){
        ApiResponse<RestaurantAdminResponse> response = restaurantAdminService.add(restaurantAdminDto,restaurantId);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PreAuthorize("hasAnyAuthority('RESTAURANT_ADMIN' , 'ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateRestaurantAdmin(@PathVariable Long id ,
                                                             @RequestBody RestaurantAdminUpdateDto updateDto){
        ApiResponse<RestaurantAdminResponse> response = restaurantAdminService.update(id,updateDto);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> generateToken(@RequestBody AuthRequest request) {
        ApiResponse<String> response=restaurantAdminService.generateToken(request);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@RequestParam Long id){
        ApiResponse<RestaurantAdminResponse> response=restaurantAdminService.deleteRestaurantAdmin(id);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
