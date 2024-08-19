package com.example.getiryemek_clone.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @OneToOne(mappedBy = "restaurant")
    private Address address;

    @Column(name = "phoneNumber",nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "restaurant")
    private List<Food> foods;

    @OneToMany(mappedBy = "restaurant")
    @JsonBackReference
    private List<RestaurantAdmin> admins;


}
