package com.example.mycart.payloads;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO extends BaseDTO
{
    private Long userId;
    private List<CartItemDTO> cartItems;
}