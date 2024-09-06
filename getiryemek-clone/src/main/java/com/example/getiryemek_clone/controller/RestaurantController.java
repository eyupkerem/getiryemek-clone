package com.example.getiryemek_clone.controller;

import com.example.getiryemek_clone.dto.response.RestaurantResponse;
import com.example.getiryemek_clone.dto.request.RestaurantDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.entity.update.RestaurantUpdateDto;
import com.example.getiryemek_clone.service.RestaurantService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/restaurant")
@SecurityRequirement(name = "BearerAuth")
@Slf4j
public class RestaurantController {

    private final RestaurantService restaurantService;
    @PreAuthorize("hasAnyAuthority('USER' , 'ADMIN' , 'RESTAURANT_ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse> getAllRestaurants () throws InterruptedException{
        ApiResponse<List<RestaurantResponse>> response = restaurantService.getAllRestaurants();
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PreAuthorize("hasAnyAuthority('USER' , 'ADMIN' , 'RESTAURANT_ADMIN')")
    @GetMapping("/{restaurantId}")
    public ResponseEntity<ApiResponse> getRestaurantById(@PathVariable Long restaurantId){
        ApiResponse<RestaurantResponse> response = restaurantService.findById(restaurantId);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PreAuthorize("hasAnyAuthority('USER' , 'ADMIN' , 'RESTAURANT_ADMIN')")
    @GetMapping("/{restaurantName}")
    public ResponseEntity<ApiResponse> getRestaurantByName(@PathVariable String restaurantName){
        ApiResponse<RestaurantResponse> response = restaurantService.findByName(restaurantName);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse> add(@RequestParam(required = false) Long addressId,
                                           @RequestBody RestaurantDto restaurantDto){
        ApiResponse<RestaurantResponse> response = restaurantService.add( addressId, restaurantDto);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN' , 'RESTAURANT_ADMIN')")
    @PutMapping("/{addressId}")
    public ResponseEntity<ApiResponse> update(HttpServletRequest httpServletRequest
            , @RequestBody RestaurantUpdateDto updateDto
            , @PathVariable("addressId") Long addressId){
        ApiResponse<RestaurantResponse> response = restaurantService.update(httpServletRequest,updateDto,addressId);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Long id){
        ApiResponse<RestaurantResponse> response = restaurantService.deleteRestaurant(id);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
