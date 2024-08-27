package com.example.getiryemek_clone.service;

import com.example.getiryemek_clone.config.PasswordEncoderConfig;
import com.example.getiryemek_clone.dto.response.CostumerResponse;
import com.example.getiryemek_clone.entity.Costumer;
import com.example.getiryemek_clone.entity.update.CostumerUpdateDto;
import com.example.getiryemek_clone.mapper.CostumerMapper;
import com.example.getiryemek_clone.repository.CostumerRepository;
import com.example.getiryemek_clone.dto.request.CostumerDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


import static com.example.getiryemek_clone.entity.enums.Role.USER;

@Service
@RequiredArgsConstructor
public class CostumerService {
    private final CostumerRepository costumerRepository;
    private final CostumerMapper costumerMapper;
    private final PasswordEncoderConfig passwordEncoderConfig;

    public ApiResponse<List<CostumerResponse>> getAllCostumers() {
        List<CostumerResponse> costumerList = costumerRepository.findAll()
                .stream()
                .map(costumer -> costumerMapper.toCostumerResponse(costumer))
                .collect(Collectors.toList());
        return ApiResponse.success("Costumer List found", costumerList);
    }

    public ApiResponse<CostumerResponse> findById(Long id) {

        return  costumerRepository.findById(id)
                .map(costumer ->
                        ApiResponse.success("Costumer founded sucesfully"
                                , costumerMapper.toCostumerResponse(costumer)))
                .orElseGet(()->ApiResponse.failure("Costumer could not founded"));
    }

    public ApiResponse<CostumerResponse> findByEmail(String email){
        return costumerRepository.findByEmail(email)
                .map(costumer ->
                        ApiResponse.success("Costumer found ", costumerMapper.toCostumerResponse(costumer)))
                .orElseGet(()->ApiResponse.failure("Costumer could not found"));
    }
    public ApiResponse<CostumerResponse> findByPhoneNumber(String phoneNumber){
        return costumerRepository.findByPhoneNumber(phoneNumber)
                .map(costumer ->
                        ApiResponse.success("Costumer found ", costumerMapper.toCostumerResponse(costumer)))
                .orElseGet(()->ApiResponse.failure("Costumer could not found"));
    }

    public ApiResponse<CostumerResponse> add(CostumerDto costumerDto) {
        if (!costumerDto.getEmail().matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {
            return ApiResponse.failure("Email must be a valid Gmail address");
        }
        boolean emailExists = costumerRepository.findByEmail(costumerDto.getEmail()).isPresent();
        boolean phoneNumberExists = costumerRepository.findByPhoneNumber(costumerDto.getPhoneNumber()).isPresent();
        if(emailExists || phoneNumberExists){
            return ApiResponse.failure("Phone number or email already in use");
        }

        Costumer newCostumer = costumerMapper.toCostumer(costumerDto);
        String hashedPassword = passwordEncoderConfig.hashPassword(costumerDto.getPassword());
        newCostumer.setPassword(hashedPassword);


        newCostumer.setRole(USER);
        costumerRepository.save(newCostumer);
        return ApiResponse.success("Costumer added successfully" , costumerMapper.toCostumerResponse(newCostumer));
    }

    public ApiResponse<CostumerResponse> deleteCostumer(Long costumerId) {
        return costumerRepository.findById(costumerId)
                .map(costumer -> {
                    costumerRepository.deleteById(costumerId);
                    return ApiResponse.success("Costumer successfully deleted"
                            ,costumerMapper.toCostumerResponse(costumer) );
                })
                .orElseGet(() -> ApiResponse.failure("Costumer could not be found"));
    }

    public ApiResponse<CostumerResponse> update(Long costumerId, CostumerUpdateDto updateDto) {

       /* if (updateDto.getName().isEmpty() ||
                updateDto.getSurname().isEmpty() ||
                updateDto.getPhoneNumber().isEmpty() ||
                updateDto.getEmail().isEmpty() ||
                updateDto.getPassword().isEmpty()) {
            return ApiResponse.failure("All fields must be non-empty");
        }

        return costumerRepository.findById(costumerId)
                .map(costumer -> {
                    costumer.setName(updateDto.getName());
                    costumer.setSurname(updateDto.getSurname());
                    costumer.setPhoneNumber(updateDto.getPhoneNumber());
                    costumer.setEmail(updateDto.getEmail());
                    costumer.setPassword(updateDto.getPassword());

                    return ApiResponse.success("Costumer updated succesfully "
                            , costumerMapper.toCostumerResponse(costumer));
                })
                .orElseGet(()-> ApiResponse.failure("Costumer could not updated"));
    */

        Costumer costumer = costumerRepository.findById(costumerId).orElse(null);
        if (costumer==null){
            return ApiResponse.failure("COSTUMER_NOT_FOUND");
        }

        if(updateDto.getSurname()!=null){
            costumer.setSurname(updateDto.getSurname());
        }

        if(updateDto.getName()!=null){
            costumer.setName(updateDto.getName());
        }
        if (updateDto.getPhoneNumber()!=null){
            costumer.setPhoneNumber(updateDto.getPhoneNumber());
        }
        if(updateDto.getEmail() != null){
            costumer.setEmail(updateDto.getEmail());
        }
        if (updateDto.getPassword() !=null){
            costumer.setPassword(passwordEncoderConfig.hashPassword(updateDto.getPassword()));
        }
        return ApiResponse.success("COSTUMER UPDATED SUCCESSFULLY" , costumerMapper.toCostumerResponse(costumer));
    }
}



