package com.example.mycart.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    private Long inventoryId;

//    @Transient
//    private List<Long> reviewsId = new ArrayList<>();
}

