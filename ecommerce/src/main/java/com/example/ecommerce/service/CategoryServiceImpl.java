package com.example.ecommerce.service;

import com.example.ecommerce.models.Category;
import com.example.ecommerce.exceptions.APIException;
import com.example.ecommerce.exceptions.ResourceNotFoundException;
import com.example.ecommerce.paylods.CategoryDTO;
import com.example.ecommerce.paylods.CategoryResponse;
import com.example.ecommerce.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getCategories(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc")?
                Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize,sort);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);

        var categories =  categoryPage.getContent();

        if (categories.isEmpty()) {
            throw new APIException("No categories found");
        }

        var categoryDto = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class)).toList();

        var response = new CategoryResponse();

        response.setContent(categoryDto);
        response.setTotalPages(categoryPage.getTotalPages());
        response.setTotalElements(categoryPage.getTotalElements());
        response.setPageSize(categoryPage.getSize());
        response.setPageNumber(categoryPage.getNumber());
        response.setLastPage(categoryPage.isLast());

        return response;
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDto) {
        var category = modelMapper.map(categoryDto, Category.class);

        var categoryByName = categoryRepository.findByCategoryName(category.getCategoryName());
        if (categoryByName != null) {
            throw new APIException("Category with name "+ category.getCategoryName() +" already exists");
        }

        var savedCategory = categoryRepository.save(category);

        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(long id) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        categoryRepository.delete(category);

        return modelMapper.map(category, CategoryDTO.class);

    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDto, long id) {
        var savedCategory =categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        var category = modelMapper.map(categoryDto, Category.class);

        savedCategory.setCategoryName(category.getCategoryName());

        var updatedCategory = categoryRepository.save(savedCategory);

        return modelMapper.map(updatedCategory, CategoryDTO.class);
    }
}
