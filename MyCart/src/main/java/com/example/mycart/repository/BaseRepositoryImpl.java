package com.example.mycart.repository;

import com.example.mycart.model.BaseEntity;
import jakarta.persistence.EntityManager;

import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.repository.query.EscapeCharacter;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;


public abstract class BaseRepositoryImpl <T extends BaseEntity<Long>, ID> extends SimpleJpaRepository<T, ID>
        implements BaseRepository<T, ID>
{

    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation,entityManager);
    }



}
