package com.example.mycart.service;

import com.example.mycart.model.Cart;
import com.example.mycart.model.CartItem;
import com.example.mycart.payloads.CartDTO;
import com.example.mycart.payloads.CartItemDTO;

import java.util.List;

public interface CartService {
    CartDTO getCartByUser(Long userId);
    CartItemDTO addItemToCart(Long userId, Long productId, int quantity);
    CartItemDTO updateCartItemQuantity(Long cartItemId, int quantity);
    CartItemDTO removeItemFromCart(Long cartItemId);
    CartDTO clearCart(Long userId);
    List<CartItem> getCartItems(Long userId);
    Cart findCartByUser(Long userId);
}
