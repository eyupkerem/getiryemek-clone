package com.example.getiryemek_clone.entity;

import com.example.getiryemek_clone.entity.enums.PaymentStatu;
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
@Table(name="Paymentt")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "amount")
    private double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "paymentStatus")
    private PaymentStatu paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "paymentType")
    private PaymentType paymentType;

    @Column(name = "paymentTime")
    private LocalDateTime paymentTime;

}
