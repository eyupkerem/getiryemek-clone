package com.example.getiryemek_clone.mapper;

import com.example.getiryemek_clone.dto.request.CategoryDto;
import com.example.getiryemek_clone.dto.response.CategoryResponse;
import com.example.getiryemek_clone.entity.Category;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-03T16:51:44+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category toCategory(CategoryDto categoryDto) {
        if ( categoryDto == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.name( categoryDto.getName() );

        return category.build();
    }

    @Override
    public CategoryDto toCategoryDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto.CategoryDtoBuilder categoryDto = CategoryDto.builder();

        categoryDto.name( category.getName() );

        return categoryDto.build();
    }

    @Override
    public CategoryResponse toCategoryResponse(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryResponse.CategoryResponseBuilder categoryResponse = CategoryResponse.builder();

        categoryResponse.name( category.getName() );

        return categoryResponse.build();
    }
}
