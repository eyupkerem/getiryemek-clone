package com.example.getiryemek_clone.controller;

import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.entity.Payment;
import com.example.getiryemek_clone.service.PaymentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@SecurityRequirement(name = "BearerAuth")
@RequiredArgsConstructor
public class   PaymentController {
    private final PaymentService paymentService;

    @PreAuthorize("hasAnyAuthority('USER', 'RESTAURANT_ADMIN' , 'ADMIN')")
    @PostMapping("/{paymentType}")
    public ResponseEntity<ApiResponse> doPayment(HttpServletRequest httpServletRequest, @PathVariable Long paymentType){
        ApiResponse<Payment> response = paymentService.doPayment(httpServletRequest,paymentType);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
