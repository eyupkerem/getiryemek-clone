package com.example.getiryemek_clone.mapper;

import com.example.getiryemek_clone.dto.request.CostumerDto;
import com.example.getiryemek_clone.dto.response.CostumerResponse;
import com.example.getiryemek_clone.entity.Costumer;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-19T10:00:59+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class CostumerMapperImpl implements CostumerMapper {

    @Override
    public Costumer toCostumer(CostumerDto costumerDto) {
        if ( costumerDto == null ) {
            return null;
        }

        Costumer costumer = new Costumer();

        return costumer;
    }

    @Override
    public CostumerDto toCostumerDto(Costumer costumer) {
        if ( costumer == null ) {
            return null;
        }

        CostumerDto costumerDto = new CostumerDto();

        return costumerDto;
    }

    @Override
    public CostumerResponse toCostumerResponse(Costumer costumer) {
        if ( costumer == null ) {
            return null;
        }

        CostumerResponse costumerResponse = new CostumerResponse();

        return costumerResponse;
    }
}
