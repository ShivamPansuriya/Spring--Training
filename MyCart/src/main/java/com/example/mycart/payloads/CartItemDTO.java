package com.example.mycart.payloads;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO
{
    private ProductDTO product;

    @NotNull
    private int quantity;
}
