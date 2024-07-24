package com.example.mycart.payloads.inheritDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO extends BaseDTO {
    private String userName;
    private LocalDateTime orderDate;
    private String status;
    private BigDecimal totalAmount;
    private Page<OrderItemDTO> orderItems;
}