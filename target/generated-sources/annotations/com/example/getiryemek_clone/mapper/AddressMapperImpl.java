package com.example.getiryemek_clone.mapper;

import com.example.getiryemek_clone.dto.request.AddressDto;
import com.example.getiryemek_clone.dto.response.AddressResponse;
import com.example.getiryemek_clone.entity.Address;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-28T10:31:43+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class AddressMapperImpl implements AddressMapper {

    @Override
    public Address toAddress(AddressDto addressDto) {
        if ( addressDto == null ) {
            return null;
        }

        Address.AddressBuilder address = Address.builder();

        address.number( addressDto.getNumber() );
        address.street( addressDto.getStreet() );
        address.city( addressDto.getCity() );
        address.zipCode( addressDto.getZipCode() );

        return address.build();
    }

    @Override
    public AddressDto toAddressDto(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressDto.AddressDtoBuilder addressDto = AddressDto.builder();

        addressDto.number( address.getNumber() );
        addressDto.street( address.getStreet() );
        addressDto.city( address.getCity() );
        addressDto.zipCode( address.getZipCode() );

        return addressDto.build();
    }

    @Override
    public AddressResponse toAddressResponse(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressResponse addressResponse = new AddressResponse();

        addressResponse.setNumber( String.valueOf( address.getNumber() ) );
        addressResponse.setStreet( address.getStreet() );
        addressResponse.setCity( address.getCity() );
        addressResponse.setZipCode( address.getZipCode() );

        return addressResponse;
    }
}
