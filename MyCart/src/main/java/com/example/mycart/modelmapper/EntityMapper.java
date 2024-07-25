package com.example.mycart.modelmapper;

import com.example.mycart.model.BaseEntity;
import com.example.mycart.payloads.BaseDTO;
import org.springframework.data.domain.Page;

public interface EntityMapper<E extends BaseEntity<Long>,D extends BaseDTO>
{
    D toDTO(E entity, int pageNo);

    E toEntity(D dto);

    default Page<D> toDTOs(Page<E> entities, int pageNo)
    {
        return entities.map(entity->toDTO(entity,pageNo));
    }
}
