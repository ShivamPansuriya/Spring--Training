package com.example.ecommerce.paylods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private long productId;
    private String productName;
    private String image;
    private String description;
    private int quantity;
    private double price;
    private double discount;
    private double specialPrice;
}
