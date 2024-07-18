package com.example.mycart.payloads;

import com.example.mycart.model.CartItem;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO
{
    private List<CartItemDTO> cartItems;
}
