package com.example.getiryemek_clone.entity.update;

import com.example.getiryemek_clone.entity.Costumer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class AddressUpdateDto {

    private String street;
    private String city;
    private String zipCode;

}
