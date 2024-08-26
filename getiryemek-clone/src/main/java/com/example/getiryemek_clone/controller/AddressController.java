package com.example.getiryemek_clone.controller;

import com.example.getiryemek_clone.dto.response.AddressResponse;
import com.example.getiryemek_clone.dto.request.AddressDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.entity.update.AddressUpdateDto;
import com.example.getiryemek_clone.service.AddressService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
@RequestMapping("/api/address")
public class AddressController{

    private final AddressService addressService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse> getAllAddress(){

        ApiResponse<List<AddressResponse>> response = addressService.getAllAddress();
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{addressId}")
    public ResponseEntity<ApiResponse> getAddressById(@PathVariable Long addressId){
        ApiResponse<AddressResponse> response=addressService.findById(addressId);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'RESTAURANT_ADMIN' , 'ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse> addAddress(
            @RequestParam(required = false) Long costumerId,
            @RequestParam(required = false) Long restaurantId,
            @RequestBody AddressDto addressDto) {

        ApiResponse<AddressResponse> response = addressService.add(costumerId, restaurantId, addressDto);
        return response.isSuccess() ? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'RESTAURANT_ADMIN' , 'ADMIN')")
    @PutMapping("/{addressId}")
    public ResponseEntity<ApiResponse> updateAddress(@PathVariable Long addressId
            , @RequestBody AddressUpdateDto updateDto){
        ApiResponse<AddressResponse> response = addressService.update(addressId,updateDto);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'RESTAURANT_ADMIN' , 'ADMIN')")
    @DeleteMapping("/{addressId}")
    public ResponseEntity<ApiResponse> deleteAddress(@PathVariable Long addressId){
        ApiResponse<AddressResponse> response = addressService.deleteAddress(addressId);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
