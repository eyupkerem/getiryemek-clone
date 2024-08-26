package com.example.getiryemek_clone.controller;

import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.dto.response.FoodResponse;
import com.example.getiryemek_clone.entity.Basket;
import com.example.getiryemek_clone.service.BasketService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/basket")
@SecurityRequirement(name = "BearerAuth")
@RequiredArgsConstructor
public class BasketController {
    private final BasketService basketService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<Basket>>> getAllBaskets() {
        ApiResponse<List<Basket>> response = basketService.getAllBaskets();
        return response.isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'RESTAURANT_ADMIN' , 'ADMIN')")
    @GetMapping("/{basketId}")
    public ResponseEntity<ApiResponse<Basket>> getAllBaskets(@PathVariable Long basketId) {
        ApiResponse<Basket> response = basketService.getById(basketId);
        return response.isSuccess()
                ? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @PreAuthorize("hasAnyAuthority('USER', 'RESTAURANT_ADMIN' , 'ADMIN')")
    @PostMapping("/{foodId}")
    public ResponseEntity<ApiResponse> addFoodToBasket(HttpServletRequest httpServletRequest
            , @PathVariable Long foodId){
        ApiResponse<FoodResponse> response = basketService.addItemToBasket(httpServletRequest,foodId);
        return response.isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'RESTAURANT_ADMIN' , 'ADMIN')")
    @DeleteMapping("/{foodId}")
    public ResponseEntity<ApiResponse> deleteItemFromBasket(HttpServletRequest httpServletRequest
            , @PathVariable Long foodId ){
        ApiResponse<FoodResponse> response = basketService.deleteItemFromBasket(httpServletRequest,foodId);
        return response.isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
