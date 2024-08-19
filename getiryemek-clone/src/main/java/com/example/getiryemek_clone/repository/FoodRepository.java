package com.example.getiryemek_clone.repository;

import com.example.getiryemek_clone.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food,Long> {
    Optional<Food> findByName(String foodName);
    Optional<List<Food>> findByCategoryId(Long categoryId);
    @Query("SELECT f FROM Food f WHERE f.restaurant.id = :restaurantId")
    List<Food> getFoodFromRestaurant(@Param("restaurantId") Long restaurantId);

    @Query("SELECT f FROM Food f WHERE f.restaurant.id = :restaurantId AND f.category.id = :categoryId")
    List<Food> getFoodByRestaurantAndCategory(@Param("restaurantId") Long restaurantId, @Param("categoryId") Long categoryId);



}
