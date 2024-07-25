package com.example.mycart.modelmapper;

import com.example.mycart.model.BaseEntity;
import com.example.mycart.payloads.BaseDTO;
import com.example.mycart.service.ProductService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class CartItemMapper<T extends BaseEntity<Long>,D extends BaseDTO> implements EntityMapper<T, D>
{
    protected abstract ProductService getProductService();

    @Override
    @Mapping(target = "productName", expression = "java(getProductsNames(entity.getProductId())")
    public abstract D toDTO(T entity, int pageNo);

    protected String getProductsNames(Long productId)
    {
        return getProductService().findById(productId).getName();
    }

    protected D mapToBaseDTO(T entity, D dto)
    {
        dto.setId(entity.getId());

        dto.setCreatedTime(entity.getCreatedTime());

        dto.setUpdatedTime(entity.getUpdatedTime());

        return dto;
    }
}
