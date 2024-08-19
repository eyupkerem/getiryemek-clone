package com.example.getiryemek_clone.mapper;

import com.example.getiryemek_clone.dto.request.CategoryDto;
import com.example.getiryemek_clone.dto.response.CategoryResponse;
import com.example.getiryemek_clone.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryDto categoryDto);
    CategoryDto toCategoryDto(Category category);
    CategoryResponse toCategoryResponse(Category category);



}
