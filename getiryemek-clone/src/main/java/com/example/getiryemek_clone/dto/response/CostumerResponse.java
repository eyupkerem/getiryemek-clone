package com.example.getiryemek_clone.dto.response;

import com.example.getiryemek_clone.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class CostumerResponse {

    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private List<Address> addresses;

}
