package com.example.getiryemek_clone.entity;

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
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "street")
    private String street;

    @Column(name = "city",nullable = false)
    private String city;

    @Column(name = "zipCode")
    private String zipCode;

    @ManyToOne
    @JoinColumn(name = "costumer_id")
    private Costumer costumer;

}
