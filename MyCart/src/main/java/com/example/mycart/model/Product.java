package com.example.mycart.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @ToString.Exclude
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Inventory inventory;

    @ToString.Exclude
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderItems> orderItems = new ArrayList<>();
}

