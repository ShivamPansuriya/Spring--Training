package com.example.mycart.payloads;

import com.example.mycart.model.Order;
import com.example.mycart.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO
{
    private long id;

    private ProductDTO product;

    private int quantity;

    private BigDecimal price;
}
