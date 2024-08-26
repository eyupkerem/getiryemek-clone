package com.example.getiryemek_clone.repository;

import com.example.getiryemek_clone.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    Optional<Object> findByName(String username);

    Optional<Admin> findByEmail(String email);


}
