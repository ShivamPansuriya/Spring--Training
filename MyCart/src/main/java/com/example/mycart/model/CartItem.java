package com.example.mycart.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem extends BaseEntity<Long>
{
    @Column(nullable = false)
    protected int quantity;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Long cartId;
}
