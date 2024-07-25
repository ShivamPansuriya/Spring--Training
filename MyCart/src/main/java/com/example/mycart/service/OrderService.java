package com.example.mycart.service;

import com.example.mycart.model.Order;
import com.example.mycart.model.OrderItems;
import com.example.mycart.payloads.OrderDTO;
import com.example.mycart.utils.OrderStatus;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService extends GenericService<Order,OrderDTO,Long>
{
    Order create(Long userId);

    List<Order> getOrdersByUser(Long userId);

    Page<Order> getOrdersByUser(Long userId, int pageNo);

    Order updateOrderStatus(Long orderId, OrderStatus status);

    Order cancelOrder(Long orderId);

    Order removeOrderItem(Long OrderId,Long productId);

    Page<Order> findOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate, int pageNo);

    Page<OrderItems> findOrderItemsByOrder(Long orderId, int pageNo);

}
