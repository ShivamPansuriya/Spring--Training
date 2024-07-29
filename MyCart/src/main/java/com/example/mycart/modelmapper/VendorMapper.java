package com.example.mycart.modelmapper;

import com.example.mycart.model.NamedEntity;
import com.example.mycart.payloads.NamedDTO;
import com.example.mycart.service.ProductService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;


@Mapper(componentModel = "spring")
public abstract class VendorMapper<T extends NamedEntity,D extends NamedDTO> implements EntityMapper<T, D>
{
    protected abstract ProductService getProductService();

    @Override
    @Mapping(target = "products", expression = "java(getProductName(entity.getId(), pageNo)")
    public abstract D toDTO(T entity, int pageNo);

    protected Page<String> getProductName(Long vendorId, int pageNo)
    {
        var products = getProductService().findProductByVendor(vendorId,pageNo);

        return products.map(NamedEntity::getName);
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