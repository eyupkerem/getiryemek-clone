package com.example.getiryemek_clone.repository;

import com.example.getiryemek_clone.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food,Integer> {
}
