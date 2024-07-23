package com.example.mycart.controller;

import com.example.mycart.payloads.OrderDTO;
import com.example.mycart.payloads.OrderItemDTO;
import com.example.mycart.payloads.TopSellingProductDTO;
import com.example.mycart.service.OrderService;
import com.example.mycart.utils.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController
{
    @Autowired
    private OrderService service;

    @PostMapping("/user/{userId}")
    public ResponseEntity<OrderDTO> createOrder(@PathVariable Long userId)
    {
        var order = service.create(userId);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long orderId)
    {
        var order = service.findById(orderId);

        return new ResponseEntity<>(order,HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getUserOrders(@PathVariable Long userId)
    {
        var orders = service.getOrdersByUser(userId);

        return new ResponseEntity<>(orders,HttpStatus.OK);
    }

    @PutMapping("/{orderId}/status/{value}")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @PathVariable OrderStatus value)
    {
        var updatedOrder = service.updateOrderStatus(orderId, value);

        return new ResponseEntity<>(updatedOrder,HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<OrderDTO> cancelOrder(@PathVariable Long orderId)
    {
        var cancelledOrder = service.cancelOrder(orderId);

        return new ResponseEntity<>(cancelledOrder,HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}/product/{productId}")
    public ResponseEntity<OrderDTO> cancelOrderItem(@PathVariable Long orderId, @PathVariable Long productId)
    {
        var cancelledOrder = service.removeOrderItem(orderId,productId);

        return new ResponseEntity<>(cancelledOrder,HttpStatus.OK);
    }

    @GetMapping("/by-date-range")
    public ResponseEntity<List<OrderDTO>> getOrdersByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate)
    {
        var orders = service.findOrdersByDateRange(startDate, endDate);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
