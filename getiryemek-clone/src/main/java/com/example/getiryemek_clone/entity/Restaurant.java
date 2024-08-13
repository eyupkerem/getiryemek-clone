package com.example.getiryemek_clone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phoneNumber",nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "restaurant")
    private List<Food> foods;

    @OneToMany(mappedBy = "restaurant")
    private List<RestaurantAdmin> admins;


}
