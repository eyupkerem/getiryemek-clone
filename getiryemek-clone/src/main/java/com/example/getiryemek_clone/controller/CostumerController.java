package com.example.getiryemek_clone.controller;

import com.example.getiryemek_clone.dto.response.CostumerResponse;
import com.example.getiryemek_clone.entity.Costumer;
import com.example.getiryemek_clone.dto.request.CostumerDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.entity.update.CostumerUpdateDto;
import com.example.getiryemek_clone.service.CostumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/costumer")
@RequiredArgsConstructor
public class CostumerController {

    private final CostumerService costumerService;

    @GetMapping
    public ResponseEntity<ApiResponse> getALlCostumer(){
        ApiResponse<List<Costumer>> response = costumerService.getAllCostumers();
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCostumerById(@PathVariable Long id){
        ApiResponse<Costumer> response = costumerService.findById(id);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{phoneNumber}")
    public ResponseEntity<ApiResponse> getCostumerByPhoneNumber(@PathVariable String phoneNumber){
        ApiResponse<CostumerResponse> response = costumerService.findByPhoneNumber(phoneNumber);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @GetMapping("/{email}")
    public ResponseEntity<ApiResponse> getCostumerByEmail(@PathVariable String email){
        ApiResponse<CostumerResponse> response = costumerService.findByEmail(email);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping("/{costumerId}")
    public ResponseEntity<ApiResponse> updateCostumer(@PathVariable Long costumerId
            , @RequestBody CostumerUpdateDto updateDto){
        ApiResponse<Costumer> response = costumerService.update(costumerId,updateDto);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping
        public ResponseEntity<ApiResponse> addCostumer(@RequestBody CostumerDto costumerDto){
            ApiResponse<CostumerResponse> response = costumerService.add(costumerDto);
            return response.isSuccess()? ResponseEntity.ok(response)
                    : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @DeleteMapping("/{costumerId}")
    public ResponseEntity<ApiResponse> deleteCostumer(@PathVariable Long costumerId){
        ApiResponse<Costumer> response = costumerService.deleteCostumer(costumerId);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


}
