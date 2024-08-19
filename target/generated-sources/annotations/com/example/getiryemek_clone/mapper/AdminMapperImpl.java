package com.example.getiryemek_clone.mapper;

import com.example.getiryemek_clone.dto.request.AdminDto;
import com.example.getiryemek_clone.dto.response.AdminResponse;
import com.example.getiryemek_clone.entity.Admin;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-19T10:00:58+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class AdminMapperImpl implements AdminMapper {

    @Override
    public Admin toAdmin(AdminDto adminDto) {
        if ( adminDto == null ) {
            return null;
        }

        Admin admin = new Admin();

        return admin;
    }

    @Override
    public AdminDto toAdminDto(Admin admin) {
        if ( admin == null ) {
            return null;
        }

        AdminDto adminDto = new AdminDto();

        return adminDto;
    }

    @Override
    public AdminResponse toAdminResponse(Admin admin) {
        if ( admin == null ) {
            return null;
        }

        AdminResponse adminResponse = new AdminResponse();

        return adminResponse;
    }
}
