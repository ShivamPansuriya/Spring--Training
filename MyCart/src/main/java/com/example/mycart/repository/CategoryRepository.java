package com.example.mycart.repository;

import com.example.mycart.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository<Category,Long>
{
    Page<Category> findCategoriesByParentCategoryIdEquals(Long parentId, Pageable page);
}
