package com.example.getiryemek_clone.entity.update;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class CostumerUpdateDto {
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String password;

}
