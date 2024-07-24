package com.example.mycart.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItems extends BaseEntity<Long>
{
    @Column(nullable = false)
    protected int quantity;

    @Column(nullable = false)
    private Long productId;

    private Long orderId;

    @Column(nullable = false)
    private BigDecimal price;
}

