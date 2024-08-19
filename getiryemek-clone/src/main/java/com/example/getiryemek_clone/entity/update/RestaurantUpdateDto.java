package com.example.getiryemek_clone.entity.update;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class RestaurantUpdateDto {
    private String name;

    private Integer addressId;

    private String phoneNumber;

}
