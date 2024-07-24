package com.example.mycart.service;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.BaseEntity;
import com.example.mycart.model.Cart;
import com.example.mycart.model.Product;
import com.example.mycart.payloads.inheritDTO.BaseDTO;
import com.example.mycart.payloads.inheritDTO.NamedDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

//@CacheConfig(cacheNames = "mycache", keyGenerator = "customKeyGenerator")
public abstract class AbstractGenericService<T extends BaseEntity<Long>, D extends BaseDTO, ID> implements GenericService<T,D, ID> {

    @Autowired
    protected ModelMapper mapper;

    @Value("${myCart.page.size}")
    private int pageSize;

    protected abstract JpaRepository<T, ID> getRepository();
    protected abstract Class<T> getEntityClass();
    protected abstract Class<D> getDtoClass();

    @Override
    @Transactional
    public T create(D dto) {
        T entity = mapper.map(dto, getEntityClass());
        return getRepository().save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public T findById(ID id) {
        var res = getRepository().findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(getEntityClass().getSimpleName(), "id", (Long) id));

        return res;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> findAll(int pageNo) {
        var page = PageRequest.of(pageNo,pageSize);

        return getRepository().findAll(page);
    }

    @Override
    @Transactional
    public T update(ID id, D dto) {
        T existingEntity = getRepository().findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(getEntityClass().getSimpleName(), "id", (Long) id));
        mapper.map(dto, existingEntity);

        existingEntity.setId((Long) id);

        return getRepository().save(existingEntity);
    }

    @Override
    @Transactional
    public T delete(ID id) {
        T entity = getRepository().findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(getEntityClass().getSimpleName(), "id", (Long) id));
        getRepository().delete(entity);

        return entity;
    }
}
