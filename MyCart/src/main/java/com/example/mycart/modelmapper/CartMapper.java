package com.example.mycart.modelmapper;

import com.example.mycart.model.*;
import com.example.mycart.payloads.BaseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class CartMapper<T extends BaseEntity<Long>,D extends BaseDTO> implements EntityMapper<T, D>
{

    @Override
    public abstract D toDTO(T entity, int pageNo);

    protected D mapToBaseDTO(T entity, D dto)
    {
        dto.setId(entity.getId());

        dto.setCreatedTime(entity.getCreatedTime());

        dto.setUpdatedTime(entity.getUpdatedTime());

        return dto;
    }
}
