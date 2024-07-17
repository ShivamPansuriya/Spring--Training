package com.example.mycart.repository;

import com.example.mycart.model.Inventory;
import com.example.mycart.model.Product;
import com.example.mycart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long>
{
    Optional<Inventory> findByProduct(Product product);

    @Query(value = "SELECT i FROM Inventory i WHERE i.quantity < :threshold AND i.product.vendor.id = :vendorId")
    List<Inventory> findLowStockInventories(@Param("threshold") int threshold, @Param("vendorId") Long vendorId);
}
