package com.example.getiryemek_clone.repository;

import com.example.getiryemek_clone.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
}
