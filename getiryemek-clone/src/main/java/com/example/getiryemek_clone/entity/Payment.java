package com.example.getiryemek_clone.entity;

import com.example.getiryemek_clone.entity.enums.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "basket_id")
    private Long basketId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_Type")
    private PaymentType paymentType;

    @Column(name = "payment_Time")
    private LocalDateTime paymentTime;
}
