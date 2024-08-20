package com.example.getiryemek_clone.controller;

import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.entity.Payment;
import com.example.getiryemek_clone.service.PaymentService;
import jakarta.persistence.FetchType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/{costumerId}/{paymentType}")
    public ResponseEntity<ApiResponse> doPayment(@PathVariable Long costumerId , @PathVariable Long paymentType){
        ApiResponse<Payment> response = paymentService.doPayment(costumerId,paymentType);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
