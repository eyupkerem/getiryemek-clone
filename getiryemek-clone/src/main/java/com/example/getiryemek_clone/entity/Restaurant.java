package com.example.getiryemek_clone.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonIgnore
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(mappedBy = "restaurant")
    private Address address;

    @Column(name = "phone_number",nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "restaurant" , cascade = CascadeType.ALL , orphanRemoval = true ,fetch = FetchType.LAZY)
    private List<Food> foods;

    @OneToMany(mappedBy = "restaurant" , cascade = CascadeType.ALL , orphanRemoval = true , fetch = FetchType.LAZY)
    @JsonIgnore
    private List<RestaurantAdmin> admins;
}
