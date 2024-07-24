package com.example.mycart.repository;

import com.example.mycart.model.Order;
import com.example.mycart.model.OrderItems;
import com.example.mycart.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.nio.file.LinkOption;
import java.util.Optional;

@Repository
public interface OrderItemsRepository extends BaseRepository<OrderItems,Long>
{
    Optional<OrderItems> findByOrderIdAndProductId(Long order, Long product);
    Page<OrderItems> findOrderItemsByOrderId(Long orderId, Pageable page);
}
