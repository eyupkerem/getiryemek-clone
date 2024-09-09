package com.example.getiryemek_clone.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CostumerDto {
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
   // private String password;
}
