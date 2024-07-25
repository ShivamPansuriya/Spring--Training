package com.example.mycart.modelmapper;

import com.example.mycart.model.BaseEntity;
import com.example.mycart.model.NamedEntity;
import com.example.mycart.payloads.BaseDTO;
import com.example.mycart.payloads.NamedDTO;
import com.example.mycart.service.CategoryService;
import com.example.mycart.service.ProductService;
import com.example.mycart.service.UserService;
import com.example.mycart.service.VendorService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ReviewMapper<T extends BaseEntity<Long>,D extends BaseDTO> implements EntityMapper<T, D>
{
    protected abstract ProductService getProductService();

    protected abstract UserService getUserService();

    @Override
    @Mapping(target = "productName", expression = "java(getProductName(entity.getProductId()))")
    @Mapping(target = "userName", expression = "java(getUserName(entity.getUserId()))")
    public abstract D toDTO(T entity, int pageNo);

    @Override
    public abstract T toEntity(D entity);


    protected String  getProductName(Long productId)
    {
        return getProductService().findById(productId).getName();
    }

    protected String  getUserName(Long userId)
    {
        return getUserService().findById(userId).getName();
    }

    public D mapToBaseDTO(T entity, D dto)
    {
        dto.setId(entity.getId());

        dto.setCreatedTime(entity.getCreatedTime());

        dto.setUpdatedTime(entity.getUpdatedTime());

        return dto;
    }
}
