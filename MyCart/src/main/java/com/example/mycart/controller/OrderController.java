package com.example.mycart.controller;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.Order;
import com.example.mycart.payloads.OrderDTO;
import com.example.mycart.service.OrderService;
import com.example.mycart.modelmapper.EntityMapper;
import com.example.mycart.utils.OrderStatus;
import com.example.mycart.utils.Validator;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.mycart.constants.Constants.ID;
import static com.example.mycart.constants.Constants.ORDER;

@RestController
@RequestMapping("/api/orders")
public class OrderController
{
    private final OrderService service;

    private final EntityMapper<Order,OrderDTO> orderMapper;

    private final Validator validator;

    public OrderController(OrderService service, EntityMapper<Order, OrderDTO> orderMapper, Validator validator) {
        this.service = service;
        this.orderMapper = orderMapper;
        this.validator = validator;
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<OrderDTO> createOrder(@PathVariable Long userId)
    {
        if(validator.validateUser(userId))
        {
            throw new ResourceNotFoundException("User",ID,userId);
        }

        var order = service.create(userId);

        return new ResponseEntity<>(orderMapper.toDTO(order,0), HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long orderId)
    {
        var order = service.findById(orderId);

        if(order==null)
        {
            throw new ResourceNotFoundException(ORDER,ID,orderId);
        }
        return new ResponseEntity<>(orderMapper.toDTO(order,0),HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<OrderDTO>> getUserOrders(@PathVariable Long userId, @RequestParam(defaultValue = "0",required = false) int pageNo)
    {
        if(validator.validateUser(userId))
        {
            throw new ResourceNotFoundException("User",ID,userId);
        }

        var orders = service.getOrdersByUser(userId, pageNo);

        return new ResponseEntity<>(orders.map(order -> orderMapper.toDTO(order,0)),HttpStatus.OK);
    }

    @PutMapping("/{orderId}/status/{value}")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @PathVariable OrderStatus value)
    {
        if(validator.validateOrder(orderId))
        {
            throw new ResourceNotFoundException(ORDER,ID,orderId);
        }

        var updatedOrder = service.updateOrderStatus(orderId, value);

        return new ResponseEntity<>(orderMapper.toDTO(updatedOrder,0),HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<OrderDTO> cancelOrder(@PathVariable Long orderId)
    {
        if(validator.validateOrder(orderId))
        {
            throw new ResourceNotFoundException(ORDER,ID,orderId);
        }

        var cancelledOrder = service.cancelOrder(orderId);

        return new ResponseEntity<>(orderMapper.toDTO(cancelledOrder,0),HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}/product/{productId}")
    public ResponseEntity<OrderDTO> cancelOrderItem(@PathVariable Long orderId, @PathVariable Long productId)
    {
        if(validator.validateOrder(orderId) && validator.validateProduct(productId))
        {
            throw new ResourceNotFoundException("Order OR product",ID,orderId);
        }
        var cancelledOrder = service.removeOrderItem(orderId,productId);

        return new ResponseEntity<>(orderMapper.toDTO(cancelledOrder,0),HttpStatus.OK);
    }

    @GetMapping("/by-date-range")
    public ResponseEntity<List<OrderDTO>> getOrdersByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0",required = false) int pageNo)
    {
        var orders = service.findOrdersByDateRange(startDate, endDate, pageNo);

        return new ResponseEntity<>(orders.stream().map(order -> orderMapper.toDTO(order,0)).toList(), HttpStatus.OK);
    }
}
