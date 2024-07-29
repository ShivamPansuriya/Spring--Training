package com.example.mycart.service;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.Cart;
import com.example.mycart.model.CartItem;
import com.example.mycart.repository.CartItemRepository;
import com.example.mycart.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@CacheConfig(cacheNames = "mycache", keyGenerator = "customKeyGenerator")
@Slf4j
public class CartServiceImpl implements CartService
{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public Cart findCartByUser(Long userId)
    {
        return cartRepository.findByUserIdAndDeleted(userId,false)
                .orElseGet(() -> {
                    log.debug("creating new cart");

                    Cart newCart = new Cart();

                    newCart.setUserId(userId);

                    return cartRepository.save(newCart);
                });
    }

    @Override
    @Transactional
    @CacheEvict
    public CartItem addItemToCart(Long userId, Long productId, int quantity) {
        var cart = findCartByUser(userId);

        var existingItem = cartItemRepository.findByCartIdAndProductIdAndDeleted(cart.getId(),productId,false);

        CartItem savedItem;

        if (existingItem.isPresent())
        {
            var cartItem = existingItem.get();

            cartItem.setQuantity(cartItem.getQuantity() + quantity);

            savedItem =  cartItemRepository.save(cartItem);
        }
        else
        {
            var newItem = new CartItem();

            newItem.setCartId(cart.getId());

            newItem.setProductId(productId);

            newItem.setQuantity(quantity);

            savedItem = cartItemRepository.save(newItem);
        }
        return savedItem;
    }

    @Override
    @Transactional
    @CacheEvict
    public CartItem updateCartItemQuantity(Long userId, Long cartItemId, int quantity)
    {
        var cartItem = cartItemRepository.findById(cartItemId).get();

        cartItem.setQuantity(quantity);

        return cartItemRepository.save(cartItem);

    }

    @Override
    @Transactional
    @CacheEvict
    public CartItem removeItemFromCart(Long userId,Long cartItemId)
    {
        var cartItem = cartItemRepository.findById(cartItemId).get();

        cartItemRepository.delete(cartItem);

        return cartItem;
    }

    @Override
    @Transactional
    @CacheEvict
    public Cart clearCart(Long userId)
    {
        var cart = findCartByUser(userId);

        var cartItems = getCartItems(userId);

        cartItems.forEach(cartItem -> cartItemRepository.delete(cartItem));

        cartRepository.delete(cart);

        return cart;
    }

    @Override
    public List<CartItem> getCartItems(Long userId)
    {
        var cart = findCartByUser(userId);

        return cartItemRepository.findCartItemsByCartIdAndDeleted(cart.getId(),false);
    }
}
