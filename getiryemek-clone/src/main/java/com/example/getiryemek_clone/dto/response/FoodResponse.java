package com.example.getiryemek_clone.dto.response;

import com.example.getiryemek_clone.entity.Category;
import com.example.getiryemek_clone.entity.Restaurant;
import com.example.getiryemek_clone.repository.FoodRepository;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class FoodResponse {
    private String name;
    private BigDecimal price;
    private String imageURL;
    private String ingredients;
    private Category category;
    private Restaurant restaurant;
}
