package com.example.mycart.repository;

import com.example.mycart.model.BaseEntity;
import com.example.mycart.model.Product;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity<Long>,ID> extends JpaSpecificationExecutor<T>, JpaRepository<T,ID>
{
}



//    Page<T> findAllByDeleted(boolean isDeleted, Pageable pageable);
//
//    @Override
//    default Page<T> findAll(Pageable pageable){
//        return findAllByDeleted(false,pageable);
//    }
//
//    Optional<T> findByIdAndDeleted(ID id,boolean isDeleted);
//
//    @Override
//    default Optional<T> findById(ID id){
//        return findByIdAndDeleted(id,false);
//    }
//
//    T updateById(ID id, T entity);
//
//    @Override
//    default void delete(T entity)
//    {
//        entity.setDeleted(true);
//        updateById((ID)entity.getId(),entity);
//    }