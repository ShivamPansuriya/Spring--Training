package com.example.mycart.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO extends BaseDTO {
    private int quantity;

    private String productName;

    private BigDecimal price;
}