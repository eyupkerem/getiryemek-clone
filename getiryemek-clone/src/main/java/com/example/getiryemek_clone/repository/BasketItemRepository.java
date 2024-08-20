package com.example.getiryemek_clone.repository;

import com.example.getiryemek_clone.entity.Basket;
import com.example.getiryemek_clone.entity.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BasketItemRepository extends JpaRepository<BasketItem, Long>{
    @Query("SELECT bi FROM BasketItem bi WHERE bi.basket.id = :basketId AND bi.food.id = :foodId")
    Optional<BasketItem> findByBasketIdAndFoodId(@Param("basketId") Long basketId, @Param("foodId") Long foodId);

    @Query("SELECT bi FROM BasketItem bi WHERE bi.basket = :basket AND bi.food.id = :foodId")
    Optional<BasketItem> findByBasketAndFoodId(@Param("basket") Basket basket, @Param("foodId") Long foodId);


}
