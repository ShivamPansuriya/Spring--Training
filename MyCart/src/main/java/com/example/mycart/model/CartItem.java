package com.example.mycart.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartItem {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;

}
