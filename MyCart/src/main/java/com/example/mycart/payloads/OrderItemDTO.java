package com.example.mycart.payloads;

import com.example.mycart.model.Order;
import com.example.mycart.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO
{
    private long id;

    private ProductDTO product;

    @NotNull
    private int quantity;

    private BigDecimal price;
}
