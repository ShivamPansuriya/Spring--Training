package com.example.mycart.modelmapper;

import com.example.mycart.model.BaseEntity;
import com.example.mycart.model.NamedEntity;
import com.example.mycart.payloads.NamedDTO;
import com.example.mycart.service.OrderService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper<T extends NamedEntity,D extends NamedDTO> implements EntityMapper<T, D> {

    protected abstract OrderService getOrderService();

    @Override
    @Mapping(target = "orders", expression = "java(getOrdersId(entity.getId())")
    public abstract D toDTO(T entity, int pageNo);

    protected List<Long> getOrdersId(Long userId)
    {
        return getOrderService().getOrdersByUser(userId)
                .stream()
                .map(BaseEntity::getId)
                .toList();
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
