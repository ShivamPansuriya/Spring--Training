package com.example.mycart.service;

import com.example.mycart.model.Order;
import com.example.mycart.model.OrderItems;
import com.example.mycart.model.Product;
import com.example.mycart.payloads.inheritDTO.OrderDTO;
import com.example.mycart.utils.OrderStatus;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService extends GenericService<Order,OrderDTO,Long>
{
    Order create(Long userId);

//    OrderDTO getOrderById(Long orderId);

    List<Order> getOrdersByUser(Long userId);

    Order updateOrderStatus(Long orderId, OrderStatus status);

    Order cancelOrder(Long orderId);

    Order removeOrderItem(Long OrderId,Long productId);

    List<Order> findOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    Page<OrderItems> findOrderItemsByOrder(Long orderId, int pageNo);
//    Page<Order> findOrderByUser(Long userId, int pageNo);


}
