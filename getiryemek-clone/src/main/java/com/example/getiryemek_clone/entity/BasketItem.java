package com.example.getiryemek_clone.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasketItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "basket_id" , nullable = false)
    @JsonBackReference
    private Basket basket;

    @ManyToOne
    @JoinColumn(name = "food_id" , nullable = false)
    private Food food;

    private int quantity;

}
