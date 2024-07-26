package com.example.mycart.repository;

import com.example.mycart.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends BaseRepository<Order,Long>
{
    List<Order> findByUserIdOrderByOrderDateDesc(Long userId);

    Page<Order> findByUserIdOrderByOrderDateDesc(Long userId, Pageable page);

    @Query(value = "SELECT o FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate ORDER BY o.orderDate DESC")
    Page<Order> findOrderBetweenDate(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable page);
}
