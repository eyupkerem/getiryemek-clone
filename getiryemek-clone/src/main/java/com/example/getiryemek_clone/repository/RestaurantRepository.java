package com.example.getiryemek_clone.repository;

import com.example.getiryemek_clone.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant,Integer> {
}
