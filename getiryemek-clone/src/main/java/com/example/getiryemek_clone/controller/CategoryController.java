package com.example.getiryemek_clone.controller;

import com.example.getiryemek_clone.dto.response.CategoryResponse;
import com.example.getiryemek_clone.entity.Category;
import com.example.getiryemek_clone.dto.request.CategoryDto;
import com.example.getiryemek_clone.dto.response.ApiResponse;
import com.example.getiryemek_clone.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCategories(){
        ApiResponse<List<Category>> response = categoryService.getAllCategories();
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId){
        ApiResponse<Category> response = categoryService.findById(categoryId);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }

    @GetMapping("/{categoryName}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String categoryName){
        ApiResponse<Category> response = categoryService.findByName(categoryName);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> addCategory(@RequestBody CategoryDto categoryDto){
        ApiResponse<CategoryResponse> response = categoryService.add(categoryDto);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@RequestParam Long categoryId) {

        ApiResponse<CategoryResponse> response = categoryService.deleteCategory(categoryId);
        return response.isSuccess()? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
