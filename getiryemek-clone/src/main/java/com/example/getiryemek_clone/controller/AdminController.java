package com.example.getiryemek_clone.controller;

import com.example.getiryemek_clone.dto.response.AdminResponse;
import com.example.getiryemek_clone.entity.Admin;
import com.example.getiryemek_clone.dto.request.AdminDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllAdmin(){

        ApiResponse<List<Admin>> response = adminService.getAllAdmins();
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<ApiResponse> getAdminById(@PathVariable Long adminId){
        ApiResponse<AdminResponse> response=adminService.findById(adminId);
        return response.isSuccess()? ResponseEntity.ok(response)
        : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addAdmin(@RequestBody AdminDto adminDto){

        ApiResponse<Admin> response = adminService.add(adminDto);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<ApiResponse> deleteAdmin(@PathVariable Long adminId){
        ApiResponse<Admin> response = adminService.deleteAdmin(adminId);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
