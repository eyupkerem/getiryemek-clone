package com.example.getiryemek_clone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private int number;

    private String street;

    @Column(name = "city",nullable = false)
    private String city;

    @Column(name = "zip_Code")
    private String zipCode;

    @ManyToOne
    @JoinColumn(name = "costumer_id")
    @JsonIgnore
    private Costumer costumer;

    @OneToOne
    @JoinColumn(name = "restaurant_id")
    @JsonIgnore
    private Restaurant restaurant;

}
