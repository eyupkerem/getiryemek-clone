package com.example.getiryemek_clone.controller;

import com.example.getiryemek_clone.dto.response.RestaurantResponse;
import com.example.getiryemek_clone.entity.Restaurant;
import com.example.getiryemek_clone.dto.request.RestaurantDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.entity.update.RestaurantUpdateDto;
import com.example.getiryemek_clone.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllRestaurants(){
        ApiResponse<List<Restaurant>> response = restaurantService.getAllRestaurants();
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<ApiResponse> getRestaurantById(@PathVariable Long restaurantId){
        ApiResponse<Restaurant> response = restaurantService.findById(restaurantId);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{restaurantName}")
    public ResponseEntity<ApiResponse> getRestaurantByName(@PathVariable String restaurantName){
        ApiResponse<Restaurant> response = restaurantService.findByName(restaurantName);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> add(@RequestParam(required = false) Long addressId,
                                           @RequestBody RestaurantDto restaurantDto){
        ApiResponse<RestaurantResponse> response = restaurantService.add( addressId, restaurantDto);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }




    @PutMapping("/{restaurantId}/{addressId}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long restaurantId
            , @RequestBody RestaurantUpdateDto updateDto
            , @PathVariable("addressId") Long addressId){
        ApiResponse<Restaurant> response = restaurantService.update(restaurantId,updateDto,addressId);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Long id){
        ApiResponse<Restaurant> response = restaurantService.deleteRestaurant(id);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
