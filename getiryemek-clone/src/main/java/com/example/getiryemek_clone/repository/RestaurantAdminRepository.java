package com.example.getiryemek_clone.repository;

import com.example.getiryemek_clone.entity.RestaurantAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantAdminRepository extends JpaRepository<RestaurantAdmin,Long> {
    Optional<RestaurantAdmin> findByName(String name);
    Optional<RestaurantAdmin> findByEmail(String email);


}
