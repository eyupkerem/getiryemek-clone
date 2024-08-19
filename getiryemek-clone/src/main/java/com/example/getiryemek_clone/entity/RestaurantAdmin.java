package com.example.getiryemek_clone.entity;

import com.example.getiryemek_clone.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class RestaurantAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "password",nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @JsonManagedReference
    private Restaurant restaurant;


    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

}
