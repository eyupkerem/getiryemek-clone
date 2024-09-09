package com.example.getiryemek_clone.service.impl;

import com.example.getiryemek_clone.config.PasswordEncoderConfig;
import com.example.getiryemek_clone.dto.request.AuthRequest;
import com.example.getiryemek_clone.dto.request.CostumerDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.dto.response.CostumerResponse;
import com.example.getiryemek_clone.entity.Costumer;
import com.example.getiryemek_clone.entity.update.CostumerUpdateDto;
import com.example.getiryemek_clone.mapper.CostumerMapper;
import com.example.getiryemek_clone.repository.CostumerRepository;
import com.example.getiryemek_clone.service.CostumerService;
import com.example.getiryemek_clone.service.JwtService;
import com.example.getiryemek_clone.service.SendEmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.getiryemek_clone.util.Validations.*;
import static com.example.getiryemek_clone.util.Validations.CUSTOMER_NOT_FOUND;
import static com.example.getiryemek_clone.entity.enums.Role.USER;

@Service
@RequiredArgsConstructor
public class CostumerServiceImpl implements CostumerService {
    private final CostumerRepository costumerRepository;
    private final CostumerMapper costumerMapper;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final AuthenticationManager authenticationManager;
    private final SendEmailService sendEmailService;
    private final JwtService jwtService;

    public ApiResponse<List<CostumerResponse>> getAllCostumers() {
        List<CostumerResponse> costumerList = costumerRepository.findAll()
                .stream()
                .map(costumer -> costumerMapper.toCostumerResponse(costumer))
                .collect(Collectors.toList());
        return ApiResponse.success(SUCCESS, costumerList);
    }

    public ApiResponse<CostumerResponse> findById(Long id) {

        return  costumerRepository.findById(id)
                .map(costumer ->
                        ApiResponse.success(SUCCESS
                                , costumerMapper.toCostumerResponse(costumer)))
                .orElseGet(()->ApiResponse.failure(CUSTOMER_NOT_FOUND));
    }

    public ApiResponse<CostumerResponse> findByEmail(String email){
        return costumerRepository.findByEmail(email)
                .map(costumer ->
                        ApiResponse.success(SUCCESS, costumerMapper.toCostumerResponse(costumer)))
                .orElseGet(()->ApiResponse.failure(CUSTOMER_NOT_FOUND));
    }
    public ApiResponse<CostumerResponse> findByPhoneNumber(String phoneNumber){
        return costumerRepository.findByPhoneNumber(phoneNumber)
                .map(costumer ->
                        ApiResponse.success(SUCCESS, costumerMapper.toCostumerResponse(costumer)))
                .orElseGet(()->ApiResponse.failure(CUSTOMER_NOT_FOUND));
    }

    public ApiResponse<CostumerResponse> add(CostumerDto costumerDto) throws MessagingException {

        if (!costumerDto.getEmail().matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {
            return ApiResponse.failure(GMAIL_MANDATORY);
        }
        boolean emailExists = costumerRepository.findByEmail(costumerDto.getEmail()).isPresent();
        boolean phoneNumberExists = costumerRepository.findByPhoneNumber(costumerDto.getPhoneNumber()).isPresent();
        if(emailExists || phoneNumberExists){
            return ApiResponse.failure(ALREADY_IN_USE);
        }

        Costumer newCostumer = costumerMapper.toCostumer(costumerDto);

        newCostumer.setRole(USER);

        costumerRepository.save(newCostumer);

        String createPasswordLink = String.format("http://localhost:8080/api/create-password?costumerId=%d", newCostumer.getId());

        sendEmailService.sendCreatePassword(newCostumer.getEmail(),
                newCostumer.getName(),
                createPasswordLink);

        return ApiResponse.success(SUCCESS, costumerMapper.toCostumerResponse(newCostumer));
    }

    @Override
    public void createPassword(Long costumerId, String newPassword ) {
        Costumer costumer = costumerRepository.findById(costumerId).orElse(null);
        String hashedPassword = passwordEncoderConfig.hashPassword(newPassword);
        costumer.setPassword(hashedPassword);
        costumerRepository.save(costumer);

    }

    public ApiResponse<CostumerResponse> deleteCostumer(Long costumerId) {
        return costumerRepository.findById(costumerId)
                .map(costumer -> {
                    costumerRepository.deleteById(costumerId);
                    return ApiResponse.success(SUCCESS
                            ,costumerMapper.toCostumerResponse(costumer) );
                })
                .orElseGet(() -> ApiResponse.failure(CUSTOMER_NOT_FOUND));
    }


    public ApiResponse<CostumerResponse> update(Long costumerId, CostumerUpdateDto updateDto) {
        Costumer costumer = costumerRepository.findById(costumerId).orElse(null);

        if (costumer==null) {
            return ApiResponse.failure(ERROR + CUSTOMER_NOT_FOUND);
        }
      if (updateDto.getEmail().isBlank() ||
                updateDto.getName().isBlank() ||
                updateDto.getSurname().isBlank() ||
                updateDto.getPhoneNumber().isBlank() ||
                updateDto.getPassword().isBlank()){
            return ApiResponse.failure(FIELDS_NOT_EMPTY);
        }

        boolean emailExists = costumerRepository.findByEmail(updateDto.getEmail()).isPresent();
        boolean phoneNumberExists = costumerRepository.findByPhoneNumber(updateDto.getPhoneNumber()).isPresent();
        if(emailExists || phoneNumberExists){
            return ApiResponse.failure(ALREADY_IN_USE);
        }

        return costumerRepository.findById(costumerId).map(
                cos->{
                    cos.setEmail(updateDto.getEmail());
                    cos.setName(updateDto.getName());
                    cos.setSurname(updateDto.getSurname());
                    cos.setPhoneNumber(updateDto.getPhoneNumber());
                    cos.setPassword(updateDto.getPassword());
                    return ApiResponse.success(SUCCESS , costumerMapper.toCostumerResponse(cos));
                }
        ).orElseGet(() -> ApiResponse.failure(ERROR));
    }


    public ApiResponse<String> generateToken(AuthRequest request) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateCostumerToken(request.email());
                return ApiResponse.success(TOKEN_GENERATED , token);
            } else {
                return ApiResponse.failure(INVALID_CREDENTIALS);
            }
        } catch (Exception e) {
            return ApiResponse.failure(AUTHENTICATION_FAILED);
        }
    }
}
