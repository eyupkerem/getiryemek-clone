package com.example.getiryemek_clone.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodDto {

    private String name;
    private BigDecimal price;
    private String imageUrl;
    private String ingredients;


}
