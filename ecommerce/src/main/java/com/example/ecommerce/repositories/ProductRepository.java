package com.example.ecommerce.repositories;

import com.example.ecommerce.models.Category;
import com.example.ecommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryOrderByPriceAsc(Category category);
    List<Product> findByProductNameContainingIgnoreCase(String productName);
    List<Product> findByProductNameLikeIgnoreCase(String productName);
}
