package com.example.mycart.service;

import com.example.mycart.exception.ApiException;
import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.Category;
import com.example.mycart.payloads.inheritDTO.CategoryDTO;
import com.example.mycart.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@CacheConfig(cacheNames = "mycache", keyGenerator = "customKeyGenerator")
public class CategoryServiceImpl extends AbstractGenericService<Category,CategoryDTO,Long> implements CategoryService
{
    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ModelMapper mapper;

//    @Override
//    public CategoryDTO createCategory(CategoryDTO categoryDTO)
//    {
//        var category = mapper.map(categoryDTO, Category.class);
//
//        var savedCategory = repository.save(category);
//
//        return mapCategory(savedCategory);
//    }

    public Category findCategoryById(Long id)
    {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category","id", id));
    }

//    @Override
//    @Cacheable
//    public CategoryDTO getCategoryById(Long id)
//    {
//        var category =  findCategoryById(id);
//        return mapCategory(category);
//    }

//    @Override
//    public List<CategoryDTO> categories() {
//        var categories = repository.findAll();
//
//        return categories.stream().map(this::mapCategory).toList();
//    }

    @Override
    @Transactional
    @CachePut
    public Category update(Long id, CategoryDTO categoryDTO)
    {
        var category = findCategoryById(id);

        var parentCategory = repository.findById(categoryDTO.getParentCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category","id", id));

        category.setName(categoryDTO.getName());

        category.setDescription(categoryDTO.getDescription());

        category.setParentCategoryId(parentCategory.getId());

        return category;
    }

    @Override
    @Transactional
    @CacheEvict
    public Category delete(Long id)
    {
        Category category = findCategoryById(id);

        if(!category.getSubCategoriesId().isEmpty())
        {
            throw new ApiException("first remove all sub categories from list and then delete");
        }
        repository.delete(category);

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
        var parentCategory = findCategoryById(parentId);

        var subCategory = findCategoryById(categoryId);

        subCategory.setParentCategoryId(parentId);

        parentCategory.addSubCategories(subCategory);

        var savedSubCategory = repository.save(subCategory);

        return savedSubCategory;
    }

    @Override
    public Category removeSubCategory(Long id) {
        var subCategory = findCategoryById(id);

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
