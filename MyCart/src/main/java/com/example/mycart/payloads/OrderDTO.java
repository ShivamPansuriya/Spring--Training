package com.example.mycart.payloads;

import com.example.mycart.model.OrderItems;
import com.example.mycart.model.User;
import com.example.mycart.utils.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO
{
    private long id;

    private LocalDateTime orderDate;

    private OrderStatus status;

    private BigDecimal totalAmount;

    private List<OrderItemDTO> orderItems;
}
