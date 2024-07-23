package com.example.mycart.service;

import com.example.mycart.payloads.CategoryDTO;

import java.util.List;

public interface CategoryService extends GenericService<CategoryDTO,Long>
{
//    CategoryDTO createCategory(CategoryDTO categoryDTO);

//    CategoryDTO getCategoryById(Long id);

//    List<CategoryDTO> categories();
//
//    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

    CategoryDTO delete(Long id);

    List<CategoryDTO> getSubcategories(Long parentId);

    CategoryDTO addSubCategory(Long CategoryId, Long ParentId);

    CategoryDTO removeSubCategory(Long id);
}
