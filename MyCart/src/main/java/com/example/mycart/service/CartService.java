package com.example.mycart.service;

import com.example.mycart.model.Cart;
import com.example.mycart.model.CartItem;
import com.example.mycart.payloads.inheritDTO.CartDTO;
import com.example.mycart.payloads.inheritDTO.CartItemDTO;

import java.util.List;

public interface CartService
{
//    Cart getCartByUser(Long userId);
    CartItem getCartItemsId(Long id);
    CartItem addItemToCart(Long userId, Long productId, int quantity);
    CartItem updateCartItemQuantity(Long userId,Long cartItemId, int quantity);
    CartItem removeItemFromCart(Long userId,Long cartItemId);
    Cart clearCart(Long userId);
    List<CartItem> getCartItems(Long userId);
    Cart findCartByUser(Long userId);
}
