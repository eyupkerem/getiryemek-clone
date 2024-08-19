package com.example.getiryemek_clone.controller;

import com.example.getiryemek_clone.dto.response.AddressResponse;
import com.example.getiryemek_clone.entity.Address;
import com.example.getiryemek_clone.dto.request.AddressDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.entity.update.AddressUpdateDto;
import com.example.getiryemek_clone.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/address")
public class AddressController{

    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllAddress(){

        ApiResponse<List<Address>> response = addressService.getAllAddress();
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<ApiResponse> getAddressById(@PathVariable Long addressId){
        ApiResponse<Address> response=addressService.findById(addressId);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addAddress(
            @RequestParam(required = false) Long costumerId,
            @RequestParam(required = false) Long restaurantId,
            @RequestBody AddressDto addressDto) {

        ApiResponse<AddressResponse> response = addressService.add(costumerId, restaurantId, addressDto);
        return response.isSuccess() ? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<ApiResponse> updateAddress(@PathVariable Long addressId
            , @RequestBody AddressUpdateDto updateDto){
        ApiResponse<Address> response = addressService.update(addressId,updateDto);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<ApiResponse> deleteAddress(@PathVariable Long addressId){
        ApiResponse<Address> response = addressService.deleteAddress(addressId);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
