package com.example.mycart.repository;

import com.example.mycart.model.Order;
import com.example.mycart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>
{
    List<Order> findByUserOrderByOrderDateDesc(User user);

    @Query(value = "SELECT o FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate ORDER BY o.orderDate DESC")
    List<Order> findOrderBetweenDate(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}
