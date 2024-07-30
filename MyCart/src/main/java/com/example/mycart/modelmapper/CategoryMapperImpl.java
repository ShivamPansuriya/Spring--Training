package com.example.mycart.modelmapper;

import com.example.mycart.model.Category;
import com.example.mycart.payloads.CategoryDTO;
import com.example.mycart.service.CategoryService;
import com.example.mycart.service.ProductService;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperImpl extends CategoryMapper<Category,CategoryDTO>
{
    private final CategoryService categoryService;

    private final ProductService productService;

    public CategoryMapperImpl(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @Override
    protected CategoryService getCategoryService() {
        return categoryService;
    }

    @Override
    protected ProductService getProductService() {
        return productService;
    }

    @Override
    public CategoryDTO toDTO(Category entity, int pageNo) {
        if(entity == null)
        {
            return null;
        }
        var dto = mapToBaseDTO(entity,new CategoryDTO());

        dto.setParentCategoryId(entity.getParentCategoryId());

        dto.setProductsNames(getProductsNames(entity.getId(),pageNo));

        dto.setSubCategoriesNames(getSubCategoriesName(entity.getId(),pageNo));

        return dto;
    }

    @Override
    public Category toEntity(CategoryDTO dto) {
        if(dto == null)
        {
            return null;
        }
        return mapToBaseEntity(dto,new Category());

    }

}
