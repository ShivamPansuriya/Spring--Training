package com.example.mycart.service;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.Category;
import com.example.mycart.payloads.CategoryDTO;
import com.example.mycart.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl extends AbstractGenericService<Category,CategoryDTO,Long> implements CategoryService
{
    @Autowired
    private CategoryRepository repository;

    @Override
    @Transactional
    public Category update(Long id, CategoryDTO categoryDTO)
    {
        var category = findById(id);

        var parentCategory = repository.findById(categoryDTO.getParentCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category","id", id));

        category.setName(categoryDTO.getName());

        category.setDescription(categoryDTO.getDescription());

        category.setParentCategoryId(parentCategory.getId());

        return category;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Category> getSubcategories(Long id, int pageNo)
    {
        return repository.findCategoriesByParentCategoryIdEquals(id, PageRequest.of(pageNo,10));
    }

    @Override
    @Transactional
    public Category addSubCategory(Long categoryId, Long parentId)
    {
        var subCategory = findById(categoryId);

        subCategory.setParentCategoryId(parentId);

        return repository.save(subCategory);

    }

    @Override
    public Category removeSubCategory(Long id) {
        var subCategory = findById(id);

        repository.delete(subCategory);

        return subCategory;
    }

    @Override
    protected JpaRepository<Category, Long> getRepository() {
        return repository;
    }

    @Override
    protected Class<Category> getEntityClass() {
        return Category.class;
    }

    @Override
    protected Class<CategoryDTO> getDtoClass() {
        return CategoryDTO.class;
    }


}
