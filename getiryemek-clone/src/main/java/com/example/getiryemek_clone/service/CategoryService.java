package com.example.getiryemek_clone.service;

import com.example.getiryemek_clone.dto.response.CategoryResponse;
import com.example.getiryemek_clone.dto.request.CategoryDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    ApiResponse<List<CategoryResponse>> getAllCategories();

    ApiResponse<CategoryResponse> findById(Long categoryId);

    ApiResponse<CategoryResponse> findByName(String categoryName);

    ApiResponse<CategoryResponse> add(CategoryDto categoryDto);

    ApiResponse<CategoryResponse> deleteCategory(Long categoryId);
}
