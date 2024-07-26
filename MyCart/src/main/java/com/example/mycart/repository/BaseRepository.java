package com.example.mycart.repository;

import com.example.mycart.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity<Long>,ID> extends JpaRepository<T,ID>, JpaSpecificationExecutor<T> {

}
