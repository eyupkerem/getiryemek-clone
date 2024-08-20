package com.example.getiryemek_clone.controller;

import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.dto.response.BasketResponse;
import com.example.getiryemek_clone.entity.Basket;
import com.example.getiryemek_clone.entity.Food;
import com.example.getiryemek_clone.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/basket")
@RequiredArgsConstructor
public class BasketController {
    private final BasketService basketService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Basket>>> getAllBaskets() {
        ApiResponse<List<Basket>> response = basketService.getAllBaskets();
        return response.isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @GetMapping("/{basketId}")
    public ResponseEntity<ApiResponse<Basket>> getAllBaskets(@PathVariable Long basketId) {
        ApiResponse<Basket> response = basketService.getById(basketId);
        return response.isSuccess()
                ? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/{costumerId}/{foodId}")
    public ResponseEntity<ApiResponse> addFoodToBasket(@PathVariable Long costumerId
            , @PathVariable Long foodId){
        ApiResponse<Food> response = basketService.addItemToBasket(costumerId,foodId);
        return response.isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @DeleteMapping("/{costumerId}/{foodId}")
    public ResponseEntity<ApiResponse> deleteItemFromBasket(@PathVariable Long costumerId
            , @PathVariable Long foodId ){
        ApiResponse<Food> response = basketService.deleteItemFromBasket(costumerId,foodId);
        return response.isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }






}
