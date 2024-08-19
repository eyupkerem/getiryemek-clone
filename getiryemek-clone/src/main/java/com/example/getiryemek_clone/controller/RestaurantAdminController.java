package com.example.getiryemek_clone.controller;

import com.example.getiryemek_clone.entity.RestaurantAdmin;
import com.example.getiryemek_clone.dto.request.RestaurantAdminDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.entity.update.RestaurantAdminUpdateDto;
import com.example.getiryemek_clone.service.RestaurantAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/restaurant-admin")
public class RestaurantAdminController {

    private final RestaurantAdminService restaurantAdminService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllRestaurantAdmin(){
        ApiResponse<List<RestaurantAdmin>> response = restaurantAdminService.getAllAdmins();
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @GetMapping("/{restaurantAdminId}")
    public ResponseEntity<ApiResponse> getAdminById(@PathVariable Long restaurantAdminId){
        ApiResponse<RestaurantAdmin> response=restaurantAdminService.findById(restaurantAdminId);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateRestaurantAdmin(@PathVariable Long id ,
                                                             @RequestBody RestaurantAdminUpdateDto updateDto){
        ApiResponse<RestaurantAdmin> response = restaurantAdminService.update(id,updateDto);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/{restaurantId}")
    public ResponseEntity<ApiResponse> addRestaurantAdmin(@RequestBody RestaurantAdminDto restaurantAdminDto ,
                                                          @PathVariable Long restaurantId){
        ApiResponse<RestaurantAdmin> response = restaurantAdminService.add(restaurantAdminDto,restaurantId);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@RequestParam Long id){
        ApiResponse<RestaurantAdmin> response=restaurantAdminService.deleteRestaurantAdmin(id);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


}
