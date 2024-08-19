package com.example.getiryemek_clone.mapper;

import com.example.getiryemek_clone.dto.request.AdminDto;
import com.example.getiryemek_clone.dto.response.AdminResponse;
import com.example.getiryemek_clone.entity.Admin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    Admin toAdmin(AdminDto adminDto);
    AdminDto toAdminDto(Admin admin);
    AdminResponse toAdminResponse(Admin admin);
}
