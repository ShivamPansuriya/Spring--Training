package com.example.mycart.service;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.Category;
import com.example.mycart.payloads.CategoryDTO;
import com.example.mycart.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService
{
    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        var category = mapper.map(categoryDTO, Category.class);

        var savedCategory = repository.save(category);

        return mapCategory(savedCategory);
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        var category =  repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category","id", id));
        return mapCategory(category);
    }

    @Override
    public List<CategoryDTO> categories() {
        var categories = repository.findAll();

        return categories.stream().map(this::mapCategory).toList();
    }

    @Override
    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        var category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category","id", id));

        var parentCategory = repository.findById(categoryDTO.getParentCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category","id", id));

        category.setName(categoryDTO.getName());

        category.setDescription(categoryDTO.getDescription());

        category.setParentCategory(parentCategory);

        return categoryDTO;
    }

    @Override
    @Transactional
    public CategoryDTO deleteCategory(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category","id", id));

        repository.delete(category);

        return mapCategory(category);
    }

    @Override
    @Transactional
    public List<CategoryDTO> getSubcategories(Long id) {
        var category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category","id", id));

        return category.getSubCategories().stream().map(this::mapCategory).toList();
    }

    @Override
    @Transactional
    public CategoryDTO addSubCategory(Long categoryId, Long parentId) {
        var parentCategory = repository.findById(parentId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","parentId", parentId));

        var subCategory = repository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Sub-Category","categoryId", categoryId));

        subCategory.setParentCategory(parentCategory);

        parentCategory.addSubCategories(subCategory);

        var savedSubCategory = repository.save(subCategory);

        return mapCategory(savedSubCategory);
    }

    private CategoryDTO mapCategory(Category category)
    {
        var response = mapper.map(category,CategoryDTO.class);
        if(category.getParentCategory()!=null)
        {
            response.setParentCategory(category.getParentCategory().getId());
        }
        return response;
    }
}
