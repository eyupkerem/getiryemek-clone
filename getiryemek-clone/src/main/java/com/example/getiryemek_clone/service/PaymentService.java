package com.example.getiryemek_clone.service;

import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.entity.Basket;
import com.example.getiryemek_clone.entity.BasketItem;
import com.example.getiryemek_clone.entity.Costumer;
import com.example.getiryemek_clone.entity.Payment;
import com.example.getiryemek_clone.entity.enums.PaymentType;
import com.example.getiryemek_clone.repository.BasketItemRepository;
import com.example.getiryemek_clone.repository.BasketRepository;
import com.example.getiryemek_clone.repository.CostumerRepository;
import com.example.getiryemek_clone.repository.PaymentRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PaymentService {

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
            throw new RuntimeException("JWT token is missing or invalid");
        }

        Long id= jwtService.extractId(token);

        Costumer costumer = costumerRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Costumer could not found")
        );

        Basket basket = basketRepository.findByCostumerId(id).orElseThrow(
                ()-> new RuntimeException("Basket could not found")
        );
        PaymentType orderPaymentType=findPaymentType(paymentType);

        List<BasketItem> items = new ArrayList<>(basket.getItems());
        basket.getItems().clear();
        basket.setTotalAmount(BigDecimal.ZERO);

        for (BasketItem item : items) {
            basketItemRepository.delete(item);
        }

        basketRepository.save(basket);

        Payment payment=paymentRepository.findByBasketId(basket.getId()).orElseGet(
                ()->{
                    Payment newPayment= new Payment();
                    newPayment.setBasketId(basket.getId());
                    newPayment.setPaymentType(orderPaymentType);
                    newPayment.setPaymentTime(LocalDateTime.now());
                    paymentRepository.save(newPayment);
                    return newPayment;
                }
        );

        String costumerEmail = costumer.getEmail();

        payment.setPaymentTime(LocalDateTime.now());
        payment.setPaymentType(orderPaymentType);
        paymentRepository.save(payment);

        sendEmailService.sendEmail(costumerEmail , "deneme1" , "deneme1");


        return ApiResponse.success("Order paid succesfully" , payment);
    }

    public PaymentType findPaymentType(Long paymentType) {
        return Stream.of(PaymentType.values())
                .filter(pt -> pt.ordinal() == paymentType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid payment type: " + paymentType));
    }

}
