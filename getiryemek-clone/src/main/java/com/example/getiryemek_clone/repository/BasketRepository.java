package com.example.getiryemek_clone.repository;

import com.example.getiryemek_clone.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BasketRepository extends JpaRepository<Basket,Long> {
    Optional<Basket> findByCostumerId(Long costumerId);
}
