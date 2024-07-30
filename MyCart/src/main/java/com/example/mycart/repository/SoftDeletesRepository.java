package com.example.mycart.repository;

import com.example.mycart.model.BaseEntity;
import org.springframework.data.repository.NoRepositoryBean;


import java.util.Optional;

@NoRepositoryBean
public interface SoftDeletesRepository<T extends BaseEntity<Long>, ID> extends BaseRepository<T,ID>{
    Optional<T> findOne(ID id);
}