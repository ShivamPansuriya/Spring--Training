package com.example.mycart.payloads;

import com.example.mycart.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO
{
    private List<CartItemDTO> cartItems;
}
