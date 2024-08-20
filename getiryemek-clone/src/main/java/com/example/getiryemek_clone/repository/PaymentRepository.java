package com.example.getiryemek_clone.repository;

import com.example.getiryemek_clone.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {
    Optional<Payment> findByBasketId(Long id);
}
