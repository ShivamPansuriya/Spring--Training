package com.example.mycart.repository;

import com.example.mycart.model.Order;
import com.example.mycart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>
{
    List<Order> findByUserOrderByOrderDateDesc(User user);
}
