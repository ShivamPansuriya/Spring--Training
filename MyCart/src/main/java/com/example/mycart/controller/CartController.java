package com.example.mycart.controller;

import com.example.mycart.payloads.CartDTO;
import com.example.mycart.payloads.CartItemDTO;
import com.example.mycart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController
{
    @Autowired
    private CartService service;

    @GetMapping("/user/{userId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable Long userId)
    {
        var cartItems = service.getCartByUser(userId);

        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    @PostMapping("/user/{userId}/product/{productId}/items")
    public ResponseEntity<CartItemDTO> addItemToCart(
            @RequestBody CartItemDTO cartItemDTO,
            @PathVariable Long userId,
            @PathVariable Long productId)
    {
        var cartItem = service.addItemToCart(userId, productId, cartItemDTO.getQuantity());

        return new ResponseEntity<>(cartItem,HttpStatus.CREATED);
    }

    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<CartItemDTO> updateItemToCart(
            @RequestBody CartItemDTO cartItemDTO,
            @PathVariable Long cartItemId)
    {
        var cartItem = service.updateCartItemQuantity(cartItemId, cartItemDTO.getQuantity());

        return new ResponseEntity<>(cartItem,HttpStatus.CREATED);
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<CartItemDTO> removeItemFromCart(@PathVariable Long cartItemId)
    {
        var deletedItem = service.removeItemFromCart(cartItemId);

        return new ResponseEntity<>(deletedItem,HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<CartDTO> clearCart(@PathVariable Long userId)
    {

        var cart = service.clearCart(userId);

        return new ResponseEntity<>(cart,HttpStatus.OK);
    }
}
