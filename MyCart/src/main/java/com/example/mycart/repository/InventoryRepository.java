package com.example.mycart.repository;

import com.example.mycart.model.Inventory;
import com.example.mycart.model.Product;
import com.example.mycart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    Optional<Inventory> findByProduct(Product product);
}
