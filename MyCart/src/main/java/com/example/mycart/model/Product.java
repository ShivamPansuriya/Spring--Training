package com.example.mycart.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends NamedEntity
{
    @Column(nullable = false)
    private BigDecimal price;

    private Long categoryId;

    private Long vendorId;
}

