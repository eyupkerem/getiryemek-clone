package com.example.getiryemek_clone.dto.response;

import com.example.getiryemek_clone.entity.Address;
import com.example.getiryemek_clone.entity.Food;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantResponse {
    private String name;
    private Address address;
    private String phoneNumber;
    private List<Food> foods;
}
