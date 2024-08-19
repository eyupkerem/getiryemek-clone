package com.example.getiryemek_clone.dto.response;

import com.example.getiryemek_clone.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantAdminResponse {
    private String name;

    private String surname;

    private String password;

    private Restaurant restaurant;
}
