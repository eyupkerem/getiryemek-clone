package com.example.getiryemek_clone.mapper;

import com.example.getiryemek_clone.dto.request.CategoryDto;
import com.example.getiryemek_clone.dto.response.CategoryResponse;
import com.example.getiryemek_clone.entity.Category;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-19T10:00:59+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category toCategory(CategoryDto categoryDto) {
        if ( categoryDto == null ) {
            return null;
        }

        int id = 0;
        String name = null;

        Category category = new Category( id, name );

        return category;
    }

    @Override
    public CategoryDto toCategoryDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto categoryDto = new CategoryDto();

        return categoryDto;
    }

    @Override
    public CategoryResponse toCategoryResponse(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryResponse categoryResponse = new CategoryResponse();

        return categoryResponse;
    }

    @Override
    public CategoryResponse toCategoryResponse(CategoryDto categoryDto) {
        if ( categoryDto == null ) {
            return null;
        }

        CategoryResponse categoryResponse = new CategoryResponse();

        return categoryResponse;
    }
}
