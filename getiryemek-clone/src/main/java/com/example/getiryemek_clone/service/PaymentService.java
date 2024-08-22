package com.example.getiryemek_clone.service;

import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.entity.Basket;
import com.example.getiryemek_clone.entity.BasketItem;
import com.example.getiryemek_clone.entity.Payment;
import com.example.getiryemek_clone.entity.enums.PaymentType;
import com.example.getiryemek_clone.repository.BasketItemRepository;
import com.example.getiryemek_clone.repository.BasketRepository;
import com.example.getiryemek_clone.repository.PaymentRepository;
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

    public ApiResponse<Payment> doPayment(Long costumerId, Long paymentType) {

        Basket basket = basketRepository.findByCostumerId(costumerId).orElseThrow(
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

        payment.setPaymentTime(LocalDateTime.now());
        payment.setPaymentType(orderPaymentType);
        paymentRepository.save(payment);

        return ApiResponse.success("Order paid succesfully" , payment);

    }

    public PaymentType findPaymentType(Long paymentType) {
        return Stream.of(PaymentType.values())
                .filter(pt -> pt.ordinal() == paymentType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid payment type: " + paymentType));
    }

}
