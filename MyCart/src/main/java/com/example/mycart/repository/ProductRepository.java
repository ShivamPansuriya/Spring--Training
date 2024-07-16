package com.example.mycart.repository;

import com.example.mycart.model.Category;
import com.example.mycart.model.Product;
import com.example.mycart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>
{
    List<Product> findByCategoryOrderByPriceAsc(Category category);

    List<Product> findByNameContainingIgnoreCase(String keyWord);
}
