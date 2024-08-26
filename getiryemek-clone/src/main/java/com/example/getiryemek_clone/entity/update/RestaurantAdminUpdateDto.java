package com.example.getiryemek_clone.entity.update;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class RestaurantAdminUpdateDto {
    private String name;
    private String surname;
    private String password;
    private String email;

}
