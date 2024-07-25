package com.example.mycart.repository;

import com.example.mycart.model.Category;
import com.example.mycart.model.Product;
import com.example.mycart.model.User;
import com.example.mycart.model.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends BaseRepository<Product,Long>
{
    @Query(value = "SELECT p from Product p join Category c On p.categoryId = c.id where p.categoryId = :categoryId or  c.parentCategoryId = :categoryId ORDER BY p.price")
    Page<Product> findByCategoryOrderByPriceAsc(@Param("categoryId") Long categoryId, Pageable page);

    Page<Product> findByNameContainingIgnoreCase(String keyWord, Pageable page);

    Page<Product> findProductsByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable page);

    @Query(value = "SELECT p from Product p where p.name = :name and p.vendorId = :vendor and p.categoryId = :category")
    Optional<Product> findByNameAndCategoryAndVendor(@Param("name") String name,@Param("category") Long category,@Param("vendor") Long vendor);

    @Query(value = "SELECT p.id, p.name, SUM(oi.quantity) as total_quantity, SUM(oi.price * oi.quantity) as total_revenue FROM order_items oi JOIN product p ON oi.product_id = p.id GROUP BY p.id, p.name ORDER BY total_quantity DESC LIMIT :limit", nativeQuery = true)
    Page<Object[]> findTopSellingProducts(int limit, Pageable page);


}
