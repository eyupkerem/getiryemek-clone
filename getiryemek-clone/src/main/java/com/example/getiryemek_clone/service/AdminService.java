package com.example.getiryemek_clone.service;

import com.example.getiryemek_clone.dto.request.AuthRequest;
import com.example.getiryemek_clone.dto.response.AdminResponse;
import com.example.getiryemek_clone.dto.request.AdminDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {

    ApiResponse<List<AdminResponse>> getAllAdmins();

    ApiResponse<AdminResponse> findById(Long adminId);

    ApiResponse<String> generateToken(AuthRequest request);

    ApiResponse<AdminResponse> add(AdminDto adminDto);

    ApiResponse<AdminResponse> deleteAdmin(Long adminId);
}
