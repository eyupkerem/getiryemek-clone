package com.example.getiryemek_clone.mapper;

import com.example.getiryemek_clone.dto.request.AdminDto;
import com.example.getiryemek_clone.dto.response.AdminResponse;
import com.example.getiryemek_clone.entity.Admin;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-09T13:28:24+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class AdminMapperImpl implements AdminMapper {

    @Override
    public Admin toAdmin(AdminDto adminDto) {
        if ( adminDto == null ) {
            return null;
        }

        Admin.AdminBuilder admin = Admin.builder();

        admin.name( adminDto.getName() );
        admin.surname( adminDto.getSurname() );
        admin.password( adminDto.getPassword() );
        admin.email( adminDto.getEmail() );

        return admin.build();
    }

    @Override
    public AdminDto toAdminDto(Admin admin) {
        if ( admin == null ) {
            return null;
        }

        AdminDto adminDto = new AdminDto();

        adminDto.setName( admin.getName() );
        adminDto.setSurname( admin.getSurname() );
        adminDto.setEmail( admin.getEmail() );
        adminDto.setPassword( admin.getPassword() );

        return adminDto;
    }

    @Override
    public AdminResponse toAdminResponse(Admin admin) {
        if ( admin == null ) {
            return null;
        }

        AdminResponse adminResponse = new AdminResponse();

        adminResponse.setName( admin.getName() );
        adminResponse.setSurname( admin.getSurname() );
        adminResponse.setEmail( admin.getEmail() );

        return adminResponse;
    }
}
