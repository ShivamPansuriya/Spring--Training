package com.example.mycart.service;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.Cart;
import com.example.mycart.model.CartItem;
import com.example.mycart.repository.CartItemRepository;
import com.example.mycart.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional
    public Cart findCartByUser(Long userId)
    {
        var user = userService.findById(userId);

        return cartRepository.findByUserId(user.getId())
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

        var product = productService.findById(productId);

        var existingItem = cartItemRepository.findByCartIdAndProductId(cart.getId(),product.getId());

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

            newItem.setProductId(product.getId());

            newItem.setQuantity(quantity);

//            cart.getCartItemsId().add(newItem);

            savedItem = cartItemRepository.save(newItem);
        }
        return savedItem;
    }

    @Override
    @Transactional
    @CacheEvict
    public CartItem updateCartItemQuantity(Long userId, Long cartItemId, int quantity)
    {
        var cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem","id",cartItemId));

        cartItem.setQuantity(quantity);

        return cartItemRepository.save(cartItem);

    }

    @Override
    @Transactional
    @CacheEvict
    public CartItem removeItemFromCart(Long userId,Long cartItemId)
    {
        var cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem","id",cartItemId));

        cartItemRepository.delete(cartItem);

        return cartItem;
    }

    @Override
    @Transactional
    @CacheEvict
    public Cart clearCart(Long userId)
    {
        var cart = findCartByUser(userId);
        cartRepository.delete(cart);
        return cart;
    }

    @Override
    public List<CartItem> getCartItems(Long userId)
    {
        var cart = findCartByUser(userId);

        return cartItemRepository.findCartItemsByCartId(cart.getId());
    }
}
