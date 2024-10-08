package com.example.getiryemek_clone.service.impl;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.dto.response.FoodResponse;
import com.example.getiryemek_clone.entity.*;
import com.example.getiryemek_clone.mapper.FoodMapper;
import com.example.getiryemek_clone.repository.BasketItemRepository;
import com.example.getiryemek_clone.repository.BasketRepository;
import com.example.getiryemek_clone.repository.CostumerRepository;
import com.example.getiryemek_clone.repository.FoodRepository;
import com.example.getiryemek_clone.service.BasketService;
import com.example.getiryemek_clone.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.getiryemek_clone.util.Validations.*;
import static com.example.getiryemek_clone.util.Validations.BASKET_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;
    private final CostumerRepository costumerRepository;
    private final FoodRepository foodRepository;
    private final BasketItemRepository basketItemRepository;
    private final FoodMapper foodMapper;
    private final JwtService jwtService;

    public ApiResponse<List<Basket>> getAllBaskets() {
        List<Basket> basketList =  basketRepository.findAll().stream().collect(Collectors.toList());
        return ApiResponse.success(SUCCESS , basketList);
    }

    public ApiResponse<Basket> getById(Long basketId) {
        Basket basket = basketRepository.findById(basketId).orElseThrow(
                () -> new RuntimeException(BASKET_NOT_FOUND)
        );

        return ApiResponse.success(SUCCESS , basket);
    }

    public ApiResponse<FoodResponse> addItemToBasket(HttpServletRequest httpServletRequest, Long foodId){

        String token = httpServletRequest.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        } else {
            throw new RuntimeException("JWT token is missing or invalid");
        }

        Long id= jwtService.extractId(token);


        Costumer costumer = costumerRepository.findById(id).orElseThrow(
                ()-> new RuntimeException(CUSTOMER_NOT_FOUND)
        );
        Food food = foodRepository.findById(foodId).orElseThrow(
                ()-> new RuntimeException(FOOD_NOT_FOUND)
        );

        Basket basket = basketRepository.findByCostumerId(id).orElseGet(
                ()->{
                    Basket newBasket = new Basket();
                    newBasket.setCostumerId(id);
                    newBasket.setItems(new ArrayList<>());
                    basketRepository.save(newBasket);
                    return newBasket;
                }
        );

        Restaurant newFoodRestaurant = food.getRestaurant();

        if(!basket.getItems().isEmpty()){
            Restaurant basketRestaurant = basket.getItems().stream().findFirst().get().getFood().getRestaurant();

            if (newFoodRestaurant != basketRestaurant ){
                return ApiResponse.failure(DIFFERENT_RESTAURANT);
            }
        }

        basketItemRepository.findByBasketAndFoodId(basket,foodId).map(
                basketItem -> {
                    basketItem.setQuantity(basketItem.getQuantity() + 1);
                    return basketItem;
                }
        ).orElseGet(() -> {
            BasketItem basketItem = new BasketItem();
            basketItem.setBasket(basket);
            basketItem.setFood(food);
            basketItem.setQuantity(1);
            basket.getItems().add(basketItem);
            return basketItem;
        });

        basketRepository.save(basket);

        updateBasketTotalAmount(basket);

        return ApiResponse.success(SUCCESS , foodMapper.toFoodResponse(food));

    }


    public void updateBasketTotalAmount(Basket basket) {

        BigDecimal totalAmount = basket.getItems().stream()
                .map(item -> item.getFood().getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        basket.setTotalAmount(totalAmount);
        basketRepository.save(basket);
    }


    @Transactional
    public ApiResponse<FoodResponse> deleteItemFromBasket(HttpServletRequest httpServletRequest , Long foodId){

        String token = httpServletRequest.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        } else {
            throw new RuntimeException("JWT token is missing or invalid");
        }

        Long id= jwtService.extractId(token);

        Costumer costumer = costumerRepository.findById(id).orElseThrow(
                ()-> new RuntimeException(CUSTOMER_NOT_FOUND)
        );
        Food food = foodRepository.findById(foodId).orElseThrow(
                ()-> new RuntimeException(FOOD_NOT_FOUND)
        );

        Basket basket = basketRepository.findByCostumerId(id).orElseThrow(
                ()-> new RuntimeException(BASKET_NOT_FOUND)
        );



        Long basketId= basket.getId();

        BasketItem deletedItem =  basketItemRepository.findByBasketIdAndFoodId(basketId , foodId).orElseThrow(
                ()-> new RuntimeException(FOOD_NOT_FOUND));

        int basketItemQuantity = deletedItem.getQuantity();

        if (basketItemQuantity == 1 ){
            basketItemRepository.delete(deletedItem);
        } else {
            deletedItem.setQuantity(basketItemQuantity - 1 );
            basketItemRepository.save(deletedItem);
        }

        updateBasketTotalAmount(basket);

        return ApiResponse.success(SUCCESS , foodMapper.toFoodResponse(deletedItem.getFood()));

    }
}
