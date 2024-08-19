package com.example.getiryemek_clone.entity.update;

import lombok.Data;

@Data
public class FoodUpdateDto {
    private String name;

    private Double price;

    private String imageURL;

    private String ingredients;

}
