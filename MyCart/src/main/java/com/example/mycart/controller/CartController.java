package com.example.mycart.controller;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.Cart;
import com.example.mycart.model.CartItem;
import com.example.mycart.modelmapper.CartItemMapper;
import com.example.mycart.modelmapper.CartMapper;
import com.example.mycart.payloads.CartDTO;
import com.example.mycart.payloads.CartItemDTO;
import com.example.mycart.service.CartService;
import com.example.mycart.utils.Validator;
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
    private CartMapper<Cart,CartDTO> cartMapper;

    @Autowired
    private CartItemMapper<CartItem,CartItemDTO> cartItemMapper;

    @Autowired
    private Validator validator;

    @GetMapping("/user/{userId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable Long userId)
    {
        if(validator.validateUser(userId))
        {
            throw new ResourceNotFoundException("User","id",userId);
        }

        var cartItems = service.findCartByUser(userId);

        return new ResponseEntity<>(cartMapper.toDTO(cartItems,0), HttpStatus.OK);
    }

    @PostMapping("/user/{userId}/product/{productId}/items")
    public ResponseEntity<CartItemDTO> addItemToCart(
            @RequestBody CartItemDTO cartItemDTO,
            @PathVariable Long userId,
            @PathVariable Long productId)
    {
        if(validator.validateUser(userId))
        {
            throw new ResourceNotFoundException("User","id",userId);
        }

        if(validator.validateProduct(productId))
        {
            throw new ResourceNotFoundException("Product","id",productId);
        }

        var cartItem = service.addItemToCart(userId, productId, cartItemDTO.getQuantity());

        return new ResponseEntity<>(cartItemMapper.toDTO(cartItem,0),HttpStatus.CREATED);
    }

    @PutMapping("/user/{userId}/items/{cartItemId}")
    public ResponseEntity<CartItemDTO> updateItemToCart(
            @RequestBody CartItemDTO cartItemDTO,
            @PathVariable Long cartItemId,
            @PathVariable Long userId)
    {
        if(validator.validateUser(userId))
        {
            throw new ResourceNotFoundException("User","id",userId);
        }
        var cartItem = service.updateCartItemQuantity(userId,cartItemId, cartItemDTO.getQuantity());

        return new ResponseEntity<>(cartItemMapper.toDTO(cartItem,0),HttpStatus.CREATED);
    }

    @DeleteMapping("/user/{userId}/items/{cartItemId}")
    public ResponseEntity<CartItemDTO> removeItemFromCart(@PathVariable Long cartItemId,
                                                          @PathVariable Long userId)
    {
        if(validator.validateUser(userId))
        {
            throw new ResourceNotFoundException("User","id",userId);
        }

        var deletedItem = service.removeItemFromCart(userId,cartItemId);

        return new ResponseEntity<>(cartItemMapper.toDTO(deletedItem,0),HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<CartDTO> clearCart(@PathVariable Long userId)
    {
        if(validator.validateUser(userId))
        {
            throw new ResourceNotFoundException("User","id",userId);
        }

        var cart = service.clearCart(userId);

        return new ResponseEntity<>(cartMapper.toDTO(cart,0),HttpStatus.OK);
    }
}
