package com.example.mycart.repository;

import com.example.mycart.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductRepositoryCustom
{
    Page<Object[]> findTopSellingProducts(int limit, Pageable page);

    Page<Product> findByCategoryOrderByPriceAsc(Long categoryId, Pageable page);

    Optional<Product> findByNameAndCategoryAndVendor(String name, Long categoryId, Long vendorId);
}