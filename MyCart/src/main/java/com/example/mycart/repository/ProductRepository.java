package com.example.mycart.repository;

import com.example.mycart.model.Category;
import com.example.mycart.model.Product;
import com.example.mycart.model.User;
import com.example.mycart.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>
{
    @Query(value = "SELECT p from Product p where p.category = :category or  p.category.parentCategory = :category ORDER BY p.price")
    List<Product> findByCategoryOrderByPriceAsc(@Param("category") Category category);

    List<Product> findByNameContainingIgnoreCase(String keyWord);

    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    @Query(value = "SELECT p from Product p where p.name = :name and p.vendor = :vendor and p.category = :category")
    Optional<Product> findByNameAndCategoryAndVendor(@Param("name") String name,@Param("category") Category category,@Param("vendor") Vendor vendor);

    @Query(value = "SELECT p.id, p.name, SUM(oi.quantity) as total_quantity, SUM(oi.price * oi.quantity) as total_revenue FROM order_items oi JOIN product p ON oi.product_id = p.id GROUP BY p.id, p.name ORDER BY total_quantity DESC LIMIT :limit", nativeQuery = true)
    List<Object[]> findTopSellingProducts(int limit);
}
