package com.example.mycart.repository;

import com.example.mycart.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends BaseRepository<Product,Long> , ProductRepositoryCustom
{
    Page<Product> findByNameContainingIgnoreCase(String keyWord, Pageable page);

    Page<Product> findProductsByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable page);
}