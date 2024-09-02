package com.example.getiryemek_clone.service.impl;

import com.example.getiryemek_clone.dto.request.CategoryDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.dto.response.CategoryResponse;
import com.example.getiryemek_clone.entity.Category;
import com.example.getiryemek_clone.mapper.CategoryMapper;
import com.example.getiryemek_clone.repository.CategoryRepository;
import com.example.getiryemek_clone.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.getiryemek_clone.util.Validations.*;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public ApiResponse<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categoryList = categoryRepository.findAll()
                .stream()
                .map(category -> categoryMapper.toCategoryResponse(category))
                .collect(Collectors.toList());
        return ApiResponse.success(SUCCESS , categoryList);
    }

    public ApiResponse<CategoryResponse> findById(Long categoryId) {

        return categoryRepository.findById(categoryId)
                .map(category -> ApiResponse.success(SUCCESS
                        , categoryMapper.toCategoryResponse(category)))
                .orElseGet(()->ApiResponse.failure(CATEGORY_NOT_FOUND));
    }

    public ApiResponse<CategoryResponse> findByName(String categoryName) {

        return categoryRepository.findByName(categoryName)
                .map(category -> ApiResponse.success(SUCCESS
                        , categoryMapper.toCategoryResponse(category)))
                .orElseGet(()->ApiResponse.failure(CATEGORY_NOT_FOUND));
    }

    public ApiResponse<CategoryResponse> add(CategoryDto categoryDto) {
        return categoryRepository.findByName(categoryDto.getName())
                .map(category -> ApiResponse.<CategoryResponse>failure(CATEGORY_ALREADY_EXIST))
                .orElseGet(() -> {
                    Category newCategory = categoryMapper.toCategory(categoryDto);
                    Category savedCategory = categoryRepository.save(newCategory);
                    return ApiResponse.success(SUCCESS,
                            categoryMapper.toCategoryResponse(savedCategory));
                });
    }

    public ApiResponse<CategoryResponse> deleteCategory(Long categoryId) {

        return categoryRepository.findById(categoryId)
                .map(category -> {
                    categoryRepository.deleteById(categoryId);
                    return ApiResponse.success(SUCCESS ,
                            categoryMapper.toCategoryResponse(category));
                })
                .orElseGet(()-> ApiResponse.failure(CATEGORY_NOT_FOUND));
    }



}
