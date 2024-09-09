package com.example.getiryemek_clone.entity;

import com.example.getiryemek_clone.entity.enums.Role;
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
    private Long id;

    @Column(nullable = false)
    private String name;

    private String surname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column
    private String password;

    @OneToMany(mappedBy = "costumer" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Address> addresses;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

}
