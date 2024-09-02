package com.example.getiryemek_clone.service.impl;

import com.example.getiryemek_clone.config.PasswordEncoderConfig;
import com.example.getiryemek_clone.dto.request.AdminDto;
import com.example.getiryemek_clone.dto.request.AuthRequest;
import com.example.getiryemek_clone.dto.response.AdminResponse;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.entity.Admin;
import com.example.getiryemek_clone.mapper.AdminMapper;
import com.example.getiryemek_clone.repository.AdminRepository;
import com.example.getiryemek_clone.service.AdminService;
import com.example.getiryemek_clone.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.getiryemek_clone.entity.enums.Role.ADMIN;
import static com.example.getiryemek_clone.util.Validations.*;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoderConfig passwordEncoder;


    public ApiResponse<List<AdminResponse>> getAllAdmins() {
        List<AdminResponse> adminList = adminRepository.findAll()
                .stream()
                .map(admin -> adminMapper.toAdminResponse(admin))
                .collect(Collectors.toList());
        return ApiResponse.success(SUCCESS, adminList);
    }

    public ApiResponse<AdminResponse> findById(Long id){
        return adminRepository.findById(id)
                .map(admin ->
                        ApiResponse.success(SUCCESS , adminMapper.toAdminResponse(admin)))
                .orElseGet(()->ApiResponse.failure(ADMIN_NOT_FOUND));
    }

    public ApiResponse<AdminResponse> add(AdminDto adminDto) {
        if (!adminDto.getEmail().matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {
            return ApiResponse.failure(GMAIL_MANDATORY);
        }
        boolean emailExists = adminRepository.findByEmail(adminDto.getEmail()).isPresent();
        if(emailExists){
            return ApiResponse.failure(EMAIL_ALREADY_IN_USE);
        }

        Admin newAdmin = adminMapper.toAdmin(adminDto);
        String hashedPassword = passwordEncoder.hashPassword(adminDto.getPassword());
        newAdmin.setPassword(hashedPassword);
        newAdmin.setRole(ADMIN);
        adminRepository.save(newAdmin);
        return ApiResponse.success(SUCCESS
                , adminMapper.toAdminResponse(newAdmin));
    }

    public ApiResponse<AdminResponse> deleteAdmin(Long adminId) {
        return adminRepository.findById(adminId)
                .map(admin -> {
                    adminRepository.deleteById(adminId);
                    return ApiResponse.success(SUCCESS
                            , adminMapper.toAdminResponse(admin));
                })
                .orElseGet(()->ApiResponse.failure(ADMIN_NOT_FOUND));
    }

    public ApiResponse<String> generateToken(AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
            if (authentication.isAuthenticated()) {
                String token =  jwtService.generateAdminToken(request.email());
                return ApiResponse.success(TOKEN_GENERATED , token);
            } else {
                return ApiResponse.failure(INVALID_CREDENTIALS);
            }
        } catch (Exception e) {
            return ApiResponse.failure(AUTHENTICATION_FAILED);
        }
    }
}
