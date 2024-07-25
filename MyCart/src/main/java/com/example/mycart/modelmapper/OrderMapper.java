package com.example.mycart.modelmapper;

import com.example.mycart.model.BaseEntity;
import com.example.mycart.payloads.BaseDTO;
import com.example.mycart.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class OrderMapper<T extends BaseEntity<Long>,D extends BaseDTO> implements EntityMapper<T, D>
{

    protected abstract UserService getUserService();

    @Override
    @Mapping(target = "userName", expression = "java(getUserName(entity.getUserId())")
    public abstract D toDTO(T entity, int pageNo);

    protected String getUserName(Long userId)
    {
        return getUserService().findById(userId).getName();
    }

    protected D mapToBaseDTO(T entity, D dto)
    {
        dto.setId(entity.getId());

        dto.setCreatedTime(entity.getCreatedTime());

        dto.setUpdatedTime(entity.getUpdatedTime());

        return dto;
    }
}
