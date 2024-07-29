package com.example.mycart.repository;

import com.example.mycart.model.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

//@Transactional
@NoRepositoryBean
public interface SoftDeletesRepository<T extends BaseEntity<Long>, ID> extends BaseRepository<T,ID>{
    Optional<T> findOne(ID id);


}