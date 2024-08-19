package com.example.getiryemek_clone.dto.response;

import com.example.getiryemek_clone.entity.Category;
import com.example.getiryemek_clone.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class FoodResponse {
    private String name;

    private double price;

    private String imageURL;

    private String ingredients;

    private Category category;

    private Restaurant restaurant;
}
