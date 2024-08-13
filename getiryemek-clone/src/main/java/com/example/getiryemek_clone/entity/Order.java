package com.example.getiryemek_clone.entity;

import com.example.getiryemek_clone.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Orderr")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "orderStatus")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "restaurant_id",nullable = false)
    private Restaurant restaurant;

    @Column(name = "orderTime")
    private LocalDateTime orderTime;

    @ManyToMany
    @JoinTable(
            name = "order_food",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id")
    )
    private List<Food> food;

    @OneToOne
    @JoinColumn(name = "payment")
    private Payment payment;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Costumer costumer;
}
