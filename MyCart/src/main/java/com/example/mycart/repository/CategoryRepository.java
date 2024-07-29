package com.example.mycart.repository;

import com.example.mycart.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends SoftDeletesRepository<Category,Long>
{
    Page<Category> findCategoriesByParentCategoryIdAndAndDeleted(Long parentId,boolean isDeleted, Pageable page);
}
