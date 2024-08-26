package com.example.getiryemek_clone.service;

import com.example.getiryemek_clone.config.PasswordEncoderConfig;
import com.example.getiryemek_clone.dto.request.AuthRequest;
import com.example.getiryemek_clone.dto.response.AdminResponse;
import com.example.getiryemek_clone.entity.Admin;
import com.example.getiryemek_clone.mapper.AdminMapper;
import com.example.getiryemek_clone.repository.AdminRepository;
import com.example.getiryemek_clone.dto.request.AdminDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.getiryemek_clone.entity.enums.Role.ADMIN;

@Service
@AllArgsConstructor
public class AdminService {
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
        return ApiResponse.success("Admin List founded ", adminList);
    }

    public ApiResponse<AdminResponse> findById(Long id){
        return adminRepository.findById(id)
                .map(admin ->
                        ApiResponse.success("Admin founded succesfully" , adminMapper.toAdminResponse(admin)))
                .orElseGet(()->ApiResponse.failure("Admin could not found"));
    }

    public ApiResponse<AdminResponse> add(AdminDto adminDto) {
        Admin newAdmin = adminMapper.toAdmin(adminDto);
        String hashedPassword = passwordEncoder.hashPassword(adminDto.getPassword());
        newAdmin.setPassword(hashedPassword);
        newAdmin.setRole(ADMIN);
        adminRepository.save(newAdmin);
        return ApiResponse.success("Admin added succesfully"
                , adminMapper.toAdminResponse(newAdmin));
    }

    public ApiResponse<AdminResponse> deleteAdmin(Long adminId) {
        return adminRepository.findById(adminId)
                .map(admin -> {
                    adminRepository.deleteById(adminId);
                    return ApiResponse.success("Admin Deleted succesfully"
                            , adminMapper.toAdminResponse(admin));
                })
                .orElseGet(()->ApiResponse.failure("Admin could not deleted"));
    }

    public ApiResponse<String> generateToken(AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
            if (authentication.isAuthenticated()) {
                String token =  jwtService.generateAdminToken(request.email());
                return ApiResponse.success("Token geenrated successfully" , token);
            } else {
                return ApiResponse.failure("Invalid credentials");
            }
        } catch (Exception e) {
            return ApiResponse.failure("Authentication failed");
        }
    }
}
