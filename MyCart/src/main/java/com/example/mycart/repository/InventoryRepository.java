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
public interface InventoryRepository extends BaseRepository<Inventory,Long>
{
    Optional<Inventory> findByProductId(Long productId);

    @Query(value = "SELECT i FROM Inventory i join Product p On p.id = i.productId WHERE i.quantity < :threshold AND p.vendorId = :vendorId")
    List<Inventory> findLowStockInventories(@Param("threshold") int threshold, @Param("vendorId") Long vendorId);
}
