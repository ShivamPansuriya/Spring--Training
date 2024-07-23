package com.example.mycart.service;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.BaseEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@CacheConfig(cacheNames = "mycache", keyGenerator = "customKeyGenerator")
public abstract class AbstractGenericService<T extends BaseEntity<T>, D, ID> implements GenericService<D, ID> {

    @Autowired
    protected ModelMapper mapper;

    protected abstract JpaRepository<T, ID> getRepository();
    protected abstract Class<T> getEntityClass();
    protected abstract Class<D> getDtoClass();

    @Override
    @Transactional
    public D create(D dto) {
        T entity = mapper.map(dto, getEntityClass());
        entity = getRepository().save(entity);
        return mapper.map(entity, getDtoClass());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable
    public D findById(ID id) {
        T entity = getRepository().findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(getEntityClass().getSimpleName(), "id", (Long) id));
        return mapper.map(entity, getDtoClass());
    }

    @Override
    @Transactional(readOnly = true)
    public List<D> findAll() {
        return getRepository().findAll().stream()
                .map(entity -> mapper.map(entity, getDtoClass()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CachePut
    public D update(ID id, D dto) {
        T existingEntity = getRepository().findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(getEntityClass().getSimpleName(), "id", (Long) id));
        mapper.map(dto, existingEntity);

        existingEntity.setId((Long) id);

        existingEntity = getRepository().save(existingEntity);
        return mapper.map(existingEntity, getDtoClass());
    }

    @Override
    @Transactional
    @CacheEvict
    public D delete(ID id) {
        T entity = getRepository().findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(getEntityClass().getSimpleName(), "id", (Long) id));
        getRepository().delete(entity);

        return mapper.map(entity, getDtoClass());
    }
}
