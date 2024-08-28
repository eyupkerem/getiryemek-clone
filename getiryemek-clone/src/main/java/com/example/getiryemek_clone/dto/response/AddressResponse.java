package com.example.getiryemek_clone.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {

    private String number;
    private String street;
    private String city;
    private String zipCode;

}
