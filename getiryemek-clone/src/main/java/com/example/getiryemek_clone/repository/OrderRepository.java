package com.example.getiryemek_clone.repository;

import com.example.getiryemek_clone.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {
}
