package com.example.getiryemek_clone.repository;

import com.example.getiryemek_clone.entity.Food;
import com.example.getiryemek_clone.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
    Optional<Restaurant> findByName(String restauranName);

    // List<Food> getFoodFromRestaurant(int RestaurantId);


}
