package com.example.getiryemek_clone.service;

import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.dto.response.BasketResponse;
import com.example.getiryemek_clone.entity.*;
import com.example.getiryemek_clone.repository.BasketItemRepository;
import com.example.getiryemek_clone.repository.BasketRepository;
import com.example.getiryemek_clone.repository.CostumerRepository;
import com.example.getiryemek_clone.repository.FoodRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasketService {
    private final BasketRepository basketRepository;
    private final CostumerRepository costumerRepository;
    private final FoodRepository foodRepository;
    private final BasketItemRepository basketItemRepository;

    public ApiResponse<List<Basket>> getAllBaskets() {
        List<Basket> basketList =  basketRepository.findAll().stream().collect(Collectors.toList());
        return ApiResponse.success("Basket List founded" , basketList);
    }

    public ApiResponse<Basket> getById(Long basketId) {
        Basket basket = basketRepository.findById(basketId).orElseThrow(
                () -> new RuntimeException("Basket could not be found")
        );

        return ApiResponse.success("Basket found successfully", basket);
    }

    public ApiResponse<Food> addItemToBasket(Long costumerId , Long foodId){

        System.out.println("Costumer Id :  " + costumerId);
        System.out.println("Food Id : " + foodId);

        Costumer costumer = costumerRepository.findById(costumerId).orElseThrow(
                ()-> new RuntimeException("costumer Could not found")
        );
        Food food = foodRepository.findById(foodId).orElseThrow(
                ()-> new RuntimeException("Food could not found")
        );

        Basket basket = basketRepository.findByCostumerId(costumerId).orElseGet(
                ()->{
                    Basket newBasket = new Basket();
                    newBasket.setCostumerId(costumerId);
                    newBasket.setItems(new ArrayList<>());
                    basketRepository.save(newBasket);
                    return newBasket;
                }
        );

        Restaurant newFoodRestaurant = food.getRestaurant();

        if(!basket.getItems().isEmpty()){
            Restaurant basketRestaurant = basket.getItems().stream().findFirst().get().getFood().getRestaurant();

            if (newFoodRestaurant != basketRestaurant ){
                return ApiResponse.failure("You can order only just one restaurant at the same time");
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

        return ApiResponse.success("Food added succesfully" , food);

    }


    public void updateBasketTotalAmount(Basket basket) {

        double totalAmount = basket.getItems().stream()
                .mapToDouble(item -> item.getFood().getPrice() * item.getQuantity())
                .sum();

        basket.setTotalAmount(totalAmount);
        basketRepository.save(basket);
    }


    @Transactional
    public ApiResponse<Food> deleteItemFromBasket(Long costumerId , Long foodId){

        Costumer costumer = costumerRepository.findById(costumerId).orElseThrow(
                ()-> new RuntimeException("costumer Could not found")
        );
        Food food = foodRepository.findById(foodId).orElseThrow(
                ()-> new RuntimeException("Food could not found")
        );

        Basket basket = basketRepository.findByCostumerId(costumerId).orElseThrow(
                ()-> new RuntimeException("Basket could not found")
        );

        Long basketId= basket.getId();


       BasketItem deletedItem =  basketItemRepository.findByBasketIdAndFoodId(basketId , foodId).orElseThrow(
               ()-> new RuntimeException("Item could not found"));

        basketItemRepository.delete(deletedItem);

        if (basketItemRepository.findByBasketIdAndFoodId(basketId, foodId).isPresent()) {
            System.out.println("Item still exists in database after delete.");
        }

        updateBasketTotalAmount(basket);

        return ApiResponse.success("Item deleted" , deletedItem.getFood());

    }



}
