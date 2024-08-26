package com.example.getiryemek_clone.repository;

import com.example.getiryemek_clone.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
    Optional<Restaurant> findByName(String restauranName);

}
