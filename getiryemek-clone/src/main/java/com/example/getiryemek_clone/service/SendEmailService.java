package com.example.getiryemek_clone.service;

import com.example.getiryemek_clone.entity.BasketItem;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Service
public interface SendEmailService {
    void sendCreatePassword(String to , String name , String link) throws MessagingException;

    void sendPaymentEmail(String to,
                          String customerName,
                          String restaurantName,
                          String restaurantPhone,
                          List<BasketItem> orderItems,
                          BigDecimal totalAmount) throws MessagingException;
}
