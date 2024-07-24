package com.example.mycart.service;

import com.example.mycart.model.Category;
import com.example.mycart.payloads.inheritDTO.CategoryDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService extends GenericService<Category,CategoryDTO,Long>
{
//    CategoryDTO createCategory(CategoryDTO categoryDTO);

//    CategoryDTO getCategoryById(Long id);

//    List<CategoryDTO> categories();
//
//    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

    Page<Category> getSubcategories(Long parentId, int pageNo);

    Category addSubCategory(Long CategoryId, Long ParentId);

    Category removeSubCategory(Long id);
}
