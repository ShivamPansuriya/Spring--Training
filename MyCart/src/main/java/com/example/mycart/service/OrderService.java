package com.example.mycart.service;

import com.example.mycart.payloads.OrderDTO;
import com.example.mycart.payloads.OrderItemDTO;
import com.example.mycart.payloads.TopSellingProductDTO;
import com.example.mycart.utils.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService
{
    OrderDTO createOrder(Long userId);

    OrderDTO getOrderById(Long orderId);

    List<OrderDTO> getOrdersByUser(Long userId);

    OrderDTO updateOrderStatus(Long orderId, OrderStatus status);

    OrderDTO cancelOrder(Long orderId);

    OrderDTO removeOrderItem(Long OrderId,Long productId);

    List<OrderDTO> findOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate);


}
