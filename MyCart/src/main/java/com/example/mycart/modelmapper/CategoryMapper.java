package com.example.mycart.modelmapper;

import com.example.mycart.model.*;
import com.example.mycart.payloads.NamedDTO;
import com.example.mycart.service.CategoryService;
import com.example.mycart.service.ProductService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public abstract class CategoryMapper<T extends NamedEntity, D extends NamedDTO> implements EntityMapper<T, D>
{
    protected abstract CategoryService getCategoryService();

    protected abstract ProductService getProductService();

    @Override
    @Mapping(target = "subCategoriesNames", expression = "java(getSubCategoriesName(entity.getId(),pageNo)")
    @Mapping(target = "productsNames", expression = "java(getProductsNames(entity.getId(),pageNo)")
    public abstract D toDTO(T entity, int pageNo);

    protected Page<String> getSubCategoriesName(Long categoryId, int pageNo)
    {
        return getCategoryService().getSubcategories(categoryId,pageNo).map(NamedEntity::getName);
    }

    protected Page<String> getProductsNames(Long categoryId, int pageNo)
    {
        return getProductService().getProductByCategory(categoryId,pageNo).map(NamedEntity::getName);
    }

    public D mapToBaseDTO(T entity, D dto)
    {
        dto.setId(entity.getId());

        dto.setCreatedTime(entity.getCreatedTime());

        dto.setUpdatedTime(entity.getUpdatedTime());

        dto.setName(entity.getName());

        dto.setDescription(entity.getDescription());

        return dto;
    }

    public T mapToBaseEntity(D dto, T entity)
    {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        return entity;
    }
}
