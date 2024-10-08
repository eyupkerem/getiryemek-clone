package com.example.getiryemek_clone.service.impl;

import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.entity.*;
import com.example.getiryemek_clone.entity.enums.PaymentType;
import com.example.getiryemek_clone.repository.BasketItemRepository;
import com.example.getiryemek_clone.repository.BasketRepository;
import com.example.getiryemek_clone.repository.CostumerRepository;
import com.example.getiryemek_clone.repository.PaymentRepository;
import com.example.getiryemek_clone.service.JwtService;
import com.example.getiryemek_clone.service.PaymentService;
import com.example.getiryemek_clone.service.SendEmailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.getiryemek_clone.util.Validations.*;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final BasketRepository basketRepository;
    private final BasketItemRepository basketItemRepository;
    private final CostumerRepository costumerRepository;
    private final JwtService jwtService;
    private final SendEmailService sendEmailService;

    public ApiResponse<Payment> doPayment(HttpServletRequest httpServletRequest, Long paymentType) {
        String token = httpServletRequest.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        } else {
            return ApiResponse.failure("JWT token is missing or invalid");
        }

        Long id = jwtService.extractId(token);

        Costumer costumer = costumerRepository.findById(id).orElseThrow(
                () -> new RuntimeException(CUSTOMER_NOT_FOUND)
        );

        Basket basket = basketRepository.findByCostumerId(id).orElseThrow(
                () -> new RuntimeException(BASKET_NOT_FOUND)
        );

        PaymentType orderPaymentType = findPaymentType(paymentType);

        if (costumer.getAddresses().isEmpty()) {
            return ApiResponse.failure(ADDRESS_NOT_FOUND);
        }

        if (basket.getItems().isEmpty()) {
            return ApiResponse.failure(BASKET_EMPTY);
        }

        Restaurant orderRestaurant = basket.getItems()
                .stream()
                .findFirst()
                .get()
                .getFood().getRestaurant();

        List<BasketItem> items = new ArrayList<>(basket.getItems());

        BigDecimal amount = basket.getTotalAmount();

        basket.getItems().clear();
        basket.setTotalAmount(BigDecimal.ZERO);

        for (BasketItem item : items) {
            basketItemRepository.delete(item);
        }

        basketRepository.save(basket);

        Payment payment = paymentRepository.findByBasketId(basket.getId()).orElseGet(() -> {
            Payment newPayment = new Payment();
            newPayment.setBasketId(basket.getId());
            newPayment.setPaymentType(orderPaymentType);
            newPayment.setPaymentTime(LocalDateTime.now());
            paymentRepository.save(newPayment);
            return newPayment;
        });

        String costumerEmail = costumer.getEmail();
        String costumerName = costumer.getName();
        String costumerSurname = costumer.getSurname();
        String costumerFullName = costumerName + " " + costumerSurname;

        try {
            sendEmailService.sendPaymentEmail(
                    costumerEmail,
                    costumerFullName,
                    orderRestaurant.getName(),
                    orderRestaurant.getPhoneNumber(),
                    items,
                    amount
            );
        } catch (MessagingException e) {
            return ApiResponse.failure(EMAIL_NOT_SENT + e.getMessage());
        }

        return ApiResponse.success(SUCCESS, payment);
    }



    public PaymentType findPaymentType(Long paymentType) {
        return Stream.of(PaymentType.values())
                .filter(pt -> pt.ordinal() == paymentType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_PAYMENT_TYPE + paymentType));

    }
}
