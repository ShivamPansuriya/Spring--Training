package com.example.mycart.repository;

import com.example.mycart.model.BaseEntity;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;


public abstract class BaseRepositoryImpl <T extends BaseEntity<Long>, ID> extends SimpleJpaRepository<T, ID>
        implements BaseRepository<T, ID>
{

    protected BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation,entityManager);
    }



}
