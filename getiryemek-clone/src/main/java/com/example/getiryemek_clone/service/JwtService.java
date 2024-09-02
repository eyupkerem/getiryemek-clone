package com.example.getiryemek_clone.service;

import com.example.getiryemek_clone.entity.enums.Role;
import com.example.getiryemek_clone.security.CostumUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface JwtService {

    String generateAdminToken(String email);

    String generateCostumerToken(String email);

    boolean validateToken(String token, UserDetails userDetails);

    String extractEmail(String token);

    Role extractRole(String token);

    Long extractId(String token);

    String generateRestaurantAdminToken(String email);
}
