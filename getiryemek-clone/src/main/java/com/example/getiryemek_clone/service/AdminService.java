package com.example.getiryemek_clone.service;

import com.example.getiryemek_clone.dto.response.AdminResponse;
import com.example.getiryemek_clone.entity.Admin;
import com.example.getiryemek_clone.mapper.AdminMapper;
import com.example.getiryemek_clone.repository.AdminRepository;
import com.example.getiryemek_clone.dto.request.AdminDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.getiryemek_clone.entity.enums.Role.ADMIN;

@Service
@AllArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;


    public ApiResponse<List<Admin>> getAllAdmins() {
        List<Admin> adminList = adminRepository.findAll()
                .stream().collect(Collectors.toList());
        return ApiResponse.success("Admin List founded ", adminList);
    }

    public ApiResponse<AdminResponse> findById(Long id){

        return adminRepository.findById(id)
                .map(admin ->
                        ApiResponse.success("Admin founded succesfully" , adminMapper.toAdminResponse(admin)))
                .orElseGet(()->ApiResponse.failure("Admin could not found"));
    }

    public ApiResponse<Admin> add(AdminDto adminDto) {
        Admin newAdmin = adminMapper.toAdmin(adminDto);
        newAdmin.setRole(ADMIN);
        adminRepository.save(newAdmin);
        return ApiResponse.success("Admin added succesfully" , newAdmin);
    }

    public ApiResponse<Admin> deleteAdmin(Long adminId) {
        return adminRepository.findById(adminId)
                .map(admin -> {
                    adminRepository.deleteById(adminId);
                    return ApiResponse.success("Admin Deleted succesfully" , admin);
                })
                .orElseGet(()->ApiResponse.failure("Admin could not deleted"));

    }
}
