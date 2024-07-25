package com.example.mycart.service;

import com.example.mycart.model.Category;
import com.example.mycart.payloads.CategoryDTO;
import org.springframework.data.domain.Page;

public interface CategoryService extends GenericService<Category,CategoryDTO,Long>
{
    Page<Category> getSubcategories(Long parentId, int pageNo);

    Category addSubCategory(Long CategoryId, Long ParentId);

    Category removeSubCategory(Long id);
}
