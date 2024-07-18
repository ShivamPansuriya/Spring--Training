package com.example.mycart.service;

import com.example.mycart.exception.ApiException;
import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.Category;
import com.example.mycart.payloads.CategoryDTO;
import com.example.mycart.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@CacheConfig(cacheNames = "mycache", keyGenerator = "customKeyGenerator")
public class CategoryServiceImpl implements CategoryService
{
    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO)
    {
        var category = mapper.map(categoryDTO, Category.class);

        var savedCategory = repository.save(category);

        return mapCategory(savedCategory);
    }

    public Category findCategoryById(Long id)
    {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category","id", id));
    }
    @Override
    @Cacheable
    public CategoryDTO getCategoryById(Long id)
    {
        var category =  findCategoryById(id);
        return mapCategory(category);
    }

    @Override
    public List<CategoryDTO> categories() {
        var categories = repository.findAll();

        return categories.stream().map(this::mapCategory).toList();
    }

    @Override
    @Transactional
    @CachePut
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO)
    {
        var category = findCategoryById(id);

        var parentCategory = repository.findById(categoryDTO.getParentCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category","id", id));

        category.setName(categoryDTO.getName());

        category.setDescription(categoryDTO.getDescription());

        category.setParentCategory(parentCategory);

        return categoryDTO;
    }

    @Override
    @Transactional
    @CacheEvict
    public CategoryDTO deleteCategory(Long id)
    {
        Category category = findCategoryById(id);

        if(!category.getSubCategories().isEmpty())
        {
            throw new ApiException("first remove all sub categories from list and then delete");
        }
        repository.delete(category);

        return mapCategory(category);
    }

    @Override
    @Transactional
    public List<CategoryDTO> getSubcategories(Long id)
    {
        var category = findCategoryById(id);

        return category.getSubCategories().stream().map(this::mapCategory).toList();
    }

    @Override
    @Transactional
    public CategoryDTO addSubCategory(Long categoryId, Long parentId)
    {
        var parentCategory = findCategoryById(parentId);

        var subCategory = findCategoryById(categoryId);

        subCategory.setParentCategory(parentCategory);

        parentCategory.addSubCategories(subCategory);

        var savedSubCategory = repository.save(subCategory);

        return mapCategory(savedSubCategory);
    }

    @Override
    public CategoryDTO removeSubCategory(Long id) {
        var subCategory = findCategoryById(id);

        var parentCategory = subCategory.getParentCategory();

        if(parentCategory == null)
            throw new ApiException("sub category is already removed");

        parentCategory.getSubCategories().remove(subCategory);

        subCategory.setParentCategory(null);

        var savedSubCategory = repository.save(subCategory);

        return mapCategory(savedSubCategory);
    }

    private CategoryDTO mapCategory(Category category)
    {
        var response = mapper.map(category,CategoryDTO.class);

        if(category.getParentCategory()!=null)
        {
            response.setParentCategoryId(category.getParentCategory().getId());
        }
        return response;
    }
}
