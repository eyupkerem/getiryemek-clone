package com.example.getiryemek_clone.repository;

import com.example.getiryemek_clone.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food,Long> {
    List<Food> findByName(String foodName);

    List<Food> findByCategoryId(Long categoryId);

    List<Food> findByRestaurantIdAndCategoryId(Long restaurantId, Long categoryId);

    @Query("SELECT f FROM Food f WHERE f.restaurant.id = :restaurantId")
    List<Food> getFoodFromRestaurant(@Param("restaurantId") Long restaurantId);
}
