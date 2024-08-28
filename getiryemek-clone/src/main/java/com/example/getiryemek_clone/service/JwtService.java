package com.example.getiryemek_clone.service;

import com.example.getiryemek_clone.entity.Admin;
import com.example.getiryemek_clone.entity.Costumer;
import com.example.getiryemek_clone.entity.RestaurantAdmin;
import com.example.getiryemek_clone.entity.enums.Role;
import com.example.getiryemek_clone.repository.AdminRepository;
import com.example.getiryemek_clone.repository.CostumerRepository;
import com.example.getiryemek_clone.repository.RestaurantAdminRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {
    private final AdminRepository adminRepository;
    private final CostumerRepository costumerRepository;
    private final RestaurantAdminRepository restaurantAdminRepository;
    @Value("${jwt.key}")
    private String SECRET;

    public String generateAdminToken(String email){
        Map<String , Object> claims = new HashMap<>();
        Admin  admin =adminRepository.findByEmail(email).orElse(null);
        log.error("ADMİN" +  admin.toString());
        claims.put("role" , Role.ADMIN);
        claims.put("id" , admin.getId());
        return createToken(claims  , email);
    }
    public String generateCostumerToken(String email){
        Map<String , Object> claims = new HashMap<>();
        Costumer costumer = costumerRepository.findByEmail(email).orElse(null);
        claims.put("role" , Role.USER);
        claims.put("id" , costumer.getId());
        return createToken(claims  , email);
    }

    public String generateRestaurantAdminToken(String email){
        Map<String , Object> claims = new HashMap<>();
        RestaurantAdmin restaurantAdmin = restaurantAdminRepository.findByEmail(email).orElse(null);
        claims.put("role" , Role.RESTAURANT_ADMIN);
        claims.put("id" , restaurantAdmin.getId());
        return createToken(claims  , email);
    }

    public Boolean validateToken(String token , UserDetails userDetails){
        String username = extractEmail(token);
        Date expirationDate = extractExpiration(token);
        System.out.println("-----------------------------");

        return userDetails.getUsername().equals(username)
                &&
                expirationDate.after(new Date()) ;
    }

    private Date extractExpiration(String token){
        try{
            Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(SECRET)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getExpiration();
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    public Long extractId(String token){
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Long id = claims.get("id", Long.class);

            return id;
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }


    public String extractEmail(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    public Role extractRole(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String role = claims.get("role", String.class);

            return Role.valueOf(role);
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }


    private String createToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration( new Date(System.currentTimeMillis() + 1000 *60*30))
                .signWith(SignatureAlgorithm.HS512,SECRET)
                .compact();
    }
}
