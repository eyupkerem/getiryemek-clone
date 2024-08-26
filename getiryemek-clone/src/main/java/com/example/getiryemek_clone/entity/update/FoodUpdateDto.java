package com.example.getiryemek_clone.entity.update;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FoodUpdateDto {
    private String name;
    private BigDecimal price;
    private String imageURL;
    private String ingredients;

}
