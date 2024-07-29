package com.example.mycart.repository;

import com.example.mycart.model.Inventory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends SoftDeletesRepository<Inventory,Long>
{
    Optional<Inventory> findByProductIdAndDeleted(Long productId, boolean idDeleted);

    @Query(value = "SELECT i FROM Inventory i join Product p On p.id = i.productId WHERE i.quantity < :threshold AND p.vendorId = :vendorId AND i.deleted = false")
    List<Inventory> findLowStockInventories(@Param("threshold") int threshold, @Param("vendorId") Long vendorId);
}
