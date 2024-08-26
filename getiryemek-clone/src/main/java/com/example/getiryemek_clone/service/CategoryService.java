package com.example.getiryemek_clone.service;

import com.example.getiryemek_clone.dto.response.CategoryResponse;
import com.example.getiryemek_clone.entity.Category;
import com.example.getiryemek_clone.entity.Food;
import com.example.getiryemek_clone.mapper.CategoryMapper;
import com.example.getiryemek_clone.repository.CategoryRepository;
import com.example.getiryemek_clone.repository.FoodRepository;
import com.example.getiryemek_clone.dto.request.CategoryDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final FoodRepository foodRepository;
    private final CategoryMapper categoryMapper;

    public ApiResponse<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categoryList = categoryRepository.findAll()
                .stream()
                .map(category -> categoryMapper.toCategoryResponse(category))
                .collect(Collectors.toList());
        return ApiResponse.success("founded" , categoryList);
    }

    public ApiResponse<CategoryResponse> findById(Long categoryId) {

        return categoryRepository.findById(categoryId)
                .map(category -> ApiResponse.success("Category founded"
                        , categoryMapper.toCategoryResponse(category)))
                .orElseGet(()->ApiResponse.failure("Category could ont found"));
    }

    public ApiResponse<CategoryResponse> findByName(String categoryName) {

        return categoryRepository.findByName(categoryName)
                .map(category -> ApiResponse.success("Category founded"
                        , categoryMapper.toCategoryResponse(category)))
                .orElseGet(()->ApiResponse.failure("Category could ont found"));
    }

    public ApiResponse<CategoryResponse> add(CategoryDto categoryDto) {
        return categoryRepository.findByName(categoryDto.getName())
                .map(category -> ApiResponse.<CategoryResponse>failure("Category already exists"))
                .orElseGet(() -> {
                    Category newCategory = categoryMapper.toCategory(categoryDto);
                    Category savedCategory = categoryRepository.save(newCategory);
                    return ApiResponse.success("Category added successfully",
                            categoryMapper.toCategoryResponse(savedCategory));
                });
    }

    public ApiResponse<CategoryResponse> deleteCategory(Long categoryId) {

        return categoryRepository.findById(categoryId)
                .map(category -> {
                    categoryRepository.deleteById(categoryId);
                    return ApiResponse.success("Category deleted sucesfully" ,
                            categoryMapper.toCategoryResponse(category));
                })
                .orElseGet(()-> ApiResponse.failure("Category could not found"));
    }
}
