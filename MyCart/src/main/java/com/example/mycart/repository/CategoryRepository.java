package com.example.mycart.repository;

import com.example.mycart.model.Category;
import com.example.mycart.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends BaseRepository<Category,Long>
{
    Page<Category> findCategoriesByParentCategoryIdEquals(Long parentId, Pageable page);
}
