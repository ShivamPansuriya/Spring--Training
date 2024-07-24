package com.example.mycart.controller;

import com.example.mycart.model.Cart;
import com.example.mycart.model.CartItem;
import com.example.mycart.payloads.inheritDTO.CartDTO;
import com.example.mycart.payloads.inheritDTO.CartItemDTO;
import com.example.mycart.service.CartService;
import com.example.mycart.utils.EntityMapper;
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
    private EntityMapper<Cart,CartDTO> cartMapper;

    @Autowired
    private EntityMapper<CartItem,CartItemDTO> cartItemMapper;

    @GetMapping("/user/{userId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable Long userId)
    {
        var cartItems = service.findCartByUser(userId);


        return new ResponseEntity<>(cartMapper.map(cartItems,10), HttpStatus.OK);
    }

    @PostMapping("/user/{userId}/product/{productId}/items")
    public ResponseEntity<CartItemDTO> addItemToCart(
            @RequestBody CartItemDTO cartItemDTO,
            @PathVariable Long userId,
            @PathVariable Long productId)
    {
        var cartItem = service.addItemToCart(userId, productId, cartItemDTO.getQuantity());

        return new ResponseEntity<>(cartItemMapper.map(cartItem,0),HttpStatus.CREATED);
    }

    @PutMapping("/user/{userId}/items/{cartItemId}")
    public ResponseEntity<CartItemDTO> updateItemToCart(
            @RequestBody CartItemDTO cartItemDTO,
            @PathVariable Long cartItemId,
            @PathVariable Long userId)
    {
        var cartItem = service.updateCartItemQuantity(userId,cartItemId, cartItemDTO.getQuantity());

        return new ResponseEntity<>(cartItemMapper.map(cartItem,0),HttpStatus.CREATED);
    }

    @DeleteMapping("/user/{userId}/items/{cartItemId}")
    public ResponseEntity<CartItemDTO> removeItemFromCart(@PathVariable Long cartItemId,
                                                          @PathVariable Long userId)
    {
        var deletedItem = service.removeItemFromCart(userId,cartItemId);

        return new ResponseEntity<>(cartItemMapper.map(deletedItem,0),HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<CartDTO> clearCart(@PathVariable Long userId)
    {

        var cart = service.clearCart(userId);

        return new ResponseEntity<>(cartMapper.map(cart,0),HttpStatus.OK);
    }
}
