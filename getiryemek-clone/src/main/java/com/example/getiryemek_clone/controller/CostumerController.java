package com.example.getiryemek_clone.controller;

import com.example.getiryemek_clone.dto.request.AuthRequest;
import com.example.getiryemek_clone.dto.response.CostumerResponse;
import com.example.getiryemek_clone.dto.request.CostumerDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.entity.update.CostumerUpdateDto;
import com.example.getiryemek_clone.service.CostumerService;
import com.example.getiryemek_clone.service.JwtService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/costumer")
@SecurityRequirement(name = "BearerAuth")
@RequiredArgsConstructor
@Slf4j
public class CostumerController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CostumerService costumerService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse> getALlCostumer(){
        ApiResponse<List<CostumerResponse>> response = costumerService.getAllCostumers();
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCostumerById(@PathVariable Long id){
        ApiResponse<CostumerResponse> response = costumerService.findById(id);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/generateToken")
    public ResponseEntity<String> generateToken(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
            if (authentication.isAuthenticated()) {
                return ResponseEntity.ok(jwtService.generateCostumerToken(request.email()));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (Exception e) {
            log.error("Authentication failed: ", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{phoneNumber}")
    public ResponseEntity<ApiResponse> getCostumerByPhoneNumber(@PathVariable String phoneNumber){
        ApiResponse<CostumerResponse> response = costumerService.findByPhoneNumber(phoneNumber);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }



    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{email}")
    public ResponseEntity<ApiResponse> getCostumerByEmail(@PathVariable String email){
        ApiResponse<CostumerResponse> response = costumerService.findByEmail(email);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PutMapping("/{costumerId}")
    public ResponseEntity<ApiResponse> updateCostumer(@PathVariable Long costumerId
            , @RequestBody CostumerUpdateDto updateDto){
        ApiResponse<CostumerResponse> response = costumerService.update(costumerId,updateDto);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping
        public ResponseEntity<ApiResponse> addCostumer(@RequestBody CostumerDto costumerDto){
            ApiResponse<CostumerResponse> response = costumerService.add(costumerDto);
            return response.isSuccess()? ResponseEntity.ok(response)
                    : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{costumerId}")
    public ResponseEntity<ApiResponse> deleteCostumer(@PathVariable Long costumerId){
        ApiResponse<CostumerResponse> response = costumerService.deleteCostumer(costumerId);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
