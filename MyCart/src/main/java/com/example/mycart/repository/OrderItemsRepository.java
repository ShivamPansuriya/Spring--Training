package com.example.mycart.repository;

import com.example.mycart.model.Order;
import com.example.mycart.model.OrderItems;
import com.example.mycart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems,Long>
{
    Optional<OrderItems> findByOrderAndProduct(Order order, Product product);
}
