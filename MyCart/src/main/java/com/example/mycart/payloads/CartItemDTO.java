package com.example.mycart.payloads;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO extends BaseDTO
{
    private int quantity;

    private String productName;
}
