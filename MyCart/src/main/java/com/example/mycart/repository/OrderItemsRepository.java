package com.example.mycart.repository;

import com.example.mycart.model.OrderItems;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemsRepository extends SoftDeletesRepository<OrderItems,Long>
{
    Optional<OrderItems> findByOrderIdAndProductId(Long order, Long product);

    @Query(value = "select * from order_items oi where oi.order_id = :orderId and oi.deleted=false", nativeQuery = true)
    Page<OrderItems> findOrderItemsByOrder(@Param("orderId") Long orderId, Pageable page);

    List<OrderItems> findOrderItemsByOrderIdEquals(Long orderId);
}
