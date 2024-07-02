package com.example.ecommerce.service;

import com.example.ecommerce.paylods.CategoryDTO;
import com.example.ecommerce.paylods.CategoryResponse;


public interface CategoryService {
    CategoryResponse getCategories(int pageNumber, int pageSize, String sortBy, String sortOrder);
    CategoryDTO addCategory(CategoryDTO categoryDto);
    CategoryDTO deleteCategory(long id);
    CategoryDTO updateCategory(CategoryDTO categoryDto, long id);
}
