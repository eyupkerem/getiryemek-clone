package com.example.getiryemek_clone.service;

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
    public ApiResponse<List<Costumer>> getAllCostumers() {
        List<Costumer> costumerList = costumerRepository.findAll()
                .stream().collect(Collectors.toList());
        return ApiResponse.success("Costumer List found", costumerList);
    }

    public ApiResponse<Costumer> findById(Long id) {

        return  costumerRepository.findById(id)
                .map(costumer ->
                        ApiResponse.success("Costumer founded sucesfully" , costumer))
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
        boolean emailExists = costumerRepository.findByEmail(costumerDto.getEmail()).isPresent();
        boolean phoneNumberExists = costumerRepository.findByPhoneNumber(costumerDto.getPhoneNumber()).isPresent();

        if(emailExists || phoneNumberExists){
            return ApiResponse.failure("Phone number or email already in use");
        }

        Costumer newCostumer = costumerMapper.toCostumer(costumerDto);
        newCostumer.setRole(USER);
        costumerRepository.save(newCostumer);
        return ApiResponse.success("Costumer added succesfully" , costumerMapper.toCostumerResponse(newCostumer));
    }

    public ApiResponse<Costumer> deleteCostumer(Long costumerId) {
        return costumerRepository.findById(costumerId)
                .map(costumer -> {
                    costumerRepository.deleteById(costumerId);
                    return ApiResponse.success("Costumer successfully deleted", costumer);
                })
                .orElseGet(() -> ApiResponse.failure("Costumer could not be found"));
    }

    public ApiResponse<Costumer> update(Long costumerId, CostumerUpdateDto updateDto) {

        if (updateDto.getName().isEmpty() ||
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

                    return ApiResponse.success("Costumer updated succesfully " , costumer);
                })
                .orElseGet(()-> ApiResponse.failure("Costumer could not updated"));
    }

}
