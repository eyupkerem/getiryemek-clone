package com.example.getiryemek_clone.mapper;

import com.example.getiryemek_clone.dto.request.CostumerDto;
import com.example.getiryemek_clone.dto.response.CostumerResponse;
import com.example.getiryemek_clone.entity.Address;
import com.example.getiryemek_clone.entity.Costumer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-26T10:12:22+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class CostumerMapperImpl implements CostumerMapper {

    @Override
    public Costumer toCostumer(CostumerDto costumerDto) {
        if ( costumerDto == null ) {
            return null;
        }

        Costumer costumer = new Costumer();

        costumer.setName( costumerDto.getName() );
        costumer.setSurname( costumerDto.getSurname() );
        costumer.setEmail( costumerDto.getEmail() );
        costumer.setPhoneNumber( costumerDto.getPhoneNumber() );
        costumer.setPassword( costumerDto.getPassword() );

        return costumer;
    }

    @Override
    public CostumerDto toCostumerDto(Costumer costumer) {
        if ( costumer == null ) {
            return null;
        }

        CostumerDto.CostumerDtoBuilder costumerDto = CostumerDto.builder();

        costumerDto.name( costumer.getName() );
        costumerDto.surname( costumer.getSurname() );
        costumerDto.phoneNumber( costumer.getPhoneNumber() );
        costumerDto.email( costumer.getEmail() );
        costumerDto.password( costumer.getPassword() );

        return costumerDto.build();
    }

    @Override
    public CostumerResponse toCostumerResponse(Costumer costumer) {
        if ( costumer == null ) {
            return null;
        }

        CostumerResponse costumerResponse = new CostumerResponse();

        costumerResponse.setName( costumer.getName() );
        costumerResponse.setSurname( costumer.getSurname() );
        costumerResponse.setEmail( costumer.getEmail() );
        costumerResponse.setPhoneNumber( costumer.getPhoneNumber() );
        List<Address> list = costumer.getAddresses();
        if ( list != null ) {
            costumerResponse.setAddresses( new ArrayList<Address>( list ) );
        }

        return costumerResponse;
    }
}
