package com.example.getiryemek_clone.dto.response;

import com.example.getiryemek_clone.entity.Category;
import com.example.getiryemek_clone.entity.Restaurant;
import com.example.getiryemek_clone.repository.FoodRepository;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class FoodResponse {
    private String name;
    private BigDecimal price;
    private String imageURL;
    private String ingredients;
    private Category category;
    private Restaurant restaurant;
}

