package com.example.getiryemek_clone.service;

import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.entity.Payment;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {
    ApiResponse<Payment> doPayment(HttpServletRequest httpServletRequest, Long paymentType) throws MessagingException;
}
