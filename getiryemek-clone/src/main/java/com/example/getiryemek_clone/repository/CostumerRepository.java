package com.example.getiryemek_clone.repository;

import com.example.getiryemek_clone.entity.Costumer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CostumerRepository extends JpaRepository<Costumer,Long> {
    Optional<Costumer> findByEmail(String email);

    Optional<Costumer> findByPhoneNumber(String phoneNumber);
}
