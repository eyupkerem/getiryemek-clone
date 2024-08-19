package com.example.getiryemek_clone.mapper;

import com.example.getiryemek_clone.dto.request.CostumerDto;
import com.example.getiryemek_clone.dto.response.CostumerResponse;
import com.example.getiryemek_clone.entity.Costumer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CostumerMapper {
    Costumer toCostumer(CostumerDto costumerDto);
    CostumerDto toCostumerDto(Costumer costumer);
    CostumerResponse toCostumerResponse(Costumer costumer);

}
