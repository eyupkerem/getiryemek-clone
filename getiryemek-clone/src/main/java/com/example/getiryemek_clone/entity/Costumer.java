package com.example.getiryemek_clone.entity;

import com.example.getiryemek_clone.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Costumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email" , nullable = false)
    private String email;

    @Column(name = "phoneNumber" , nullable = false)
    private String phoneNumber;

    @Column(name = "password",nullable = false)
    private String password;

    @OneToMany(mappedBy = "costumer")
    private List<Address> addresses;


    @OneToMany(mappedBy = "costumer")
    private List<Order> orders;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

}
