package com.example.mycart.modelmapper;

import com.example.mycart.model.NamedEntity;
import com.example.mycart.payloads.NamedDTO;
import com.example.mycart.service.CategoryService;
import com.example.mycart.service.VendorService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ProductMapper<T extends NamedEntity,D extends NamedDTO> implements EntityMapper<T, D>
{
    protected abstract CategoryService getCategoryService();

    protected abstract VendorService getVendorService();

    @Override
    @Mapping(target = "categoryName", expression = "java(getCategoryName(entity.getCategoryId()))")
    @Mapping(target = "vendorName", expression = "java(getVendorName(entity.getVendorId()))")
    public abstract D toDTO(T entity, int pageNo);

    protected String  getCategoryName(Long categoryId)
    {
        return getCategoryService().findById(categoryId).getName();
    }

    protected String  getVendorName(Long vendorId)
    {
        return getVendorService().findById(vendorId).getName();
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

    protected T mapToBaseEntity(D dto, T entity)
    {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        return entity;
    }
}
