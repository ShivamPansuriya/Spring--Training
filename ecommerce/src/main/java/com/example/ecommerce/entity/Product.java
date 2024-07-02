package com.example.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    private long productId;

    @NotBlank
    private String productName;

    private String description;

    private String image;

    @NotEmpty
    @NotBlank
    private int quantity;

    @NotEmpty
    @NotBlank
    private double price;

    private double discount;

    private double specialPrice;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;
}
