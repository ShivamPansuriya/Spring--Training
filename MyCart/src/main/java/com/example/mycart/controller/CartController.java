package com.example.mycart.controller;

import com.example.mycart.payloads.CartDTO;
import com.example.mycart.payloads.CartItemDTO;
import com.example.mycart.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@Slf4j
public class CartController
{
    @Autowired
    private CartService service;
    @Autowired
    private HttpServletRequest httpServletRequest;

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

    @PutMapping("/user/{userId}/items/{cartItemId}")
    public ResponseEntity<CartItemDTO> updateItemToCart(
            @RequestBody CartItemDTO cartItemDTO,
            @PathVariable Long cartItemId,
            @PathVariable Long userId)
    {
        var cartItem = service.updateCartItemQuantity(userId,cartItemId, cartItemDTO.getQuantity());

        return new ResponseEntity<>(cartItem,HttpStatus.CREATED);
    }

    @DeleteMapping("/user/{userId}/items/{cartItemId}")
    public ResponseEntity<CartItemDTO> removeItemFromCart(@PathVariable Long cartItemId,
                                                          @PathVariable Long userId)
    {
        var deletedItem = service.removeItemFromCart(userId,cartItemId);

        return new ResponseEntity<>(deletedItem,HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<CartDTO> clearCart(@PathVariable Long userId)
    {

        var cart = service.clearCart(userId);

        return new ResponseEntity<>(cart,HttpStatus.OK);
    }
}
