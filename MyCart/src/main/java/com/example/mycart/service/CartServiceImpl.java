package com.example.mycart.service;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.Cart;
import com.example.mycart.model.CartItem;
import com.example.mycart.payloads.CartDTO;
import com.example.mycart.payloads.CartItemDTO;
import com.example.mycart.repository.CartItemRepository;
import com.example.mycart.repository.CartRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
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
    public Cart findCartByUser(Long userId)
    {
        var user = userService.findUserById(userId);

        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    user.setCart(newCart);
                    return cartRepository.save(newCart);
                });
    }

    @Override
    public CartDTO getCartByUser(Long userId)
    {
        var cart =  findCartByUser(userId);
        return mapper.map(cart,CartDTO.class);
    }

    @Override
    @Transactional
    public CartItemDTO addItemToCart(Long userId, Long productId, int quantity) {
        var cart = findCartByUser(userId);

        var product = productService.findByProductId(productId);

        var existingItem = cartItemRepository.findByCartAndProduct(cart, product);

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

            newItem.setCart(cart);

            newItem.setProduct(product);

            newItem.setQuantity(quantity);

            cart.getCartItems().add(newItem);

            product.getCartItems().add(newItem);

            savedItem = cartItemRepository.save(newItem);
        }
        return mapper.map(savedItem,CartItemDTO.class);
    }

    @Override
    @Transactional
    public CartItemDTO updateCartItemQuantity(Long cartItemId, int quantity)
    {
        var cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem","id",cartItemId));

        cartItem.setQuantity(quantity);

        var saveItem = cartItemRepository.save(cartItem);

        return mapper.map(saveItem,CartItemDTO.class);
    }

    @Override
    @Transactional
    public CartItemDTO removeItemFromCart(Long cartItemId)
    {
        var cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem","id",cartItemId));

        cartItem.getCart().getCartItems().remove(cartItem);

        cartItem.getProduct().getCartItems().remove(cartItem);

        return mapper.map(cartItem,CartItemDTO.class);
    }

    @Override
    @Transactional
    public CartDTO clearCart(Long userId)
    {
        var cart = findCartByUser(userId);
        var user = userService.findUserById(userId);
        user.setCart(null);
        cartRepository.delete(cart);
        return mapper.map(cart,CartDTO.class);
    }

    @Override
    public List<CartItem> getCartItems(Long userId)
    {
        var cart = findCartByUser(userId);

        return cart.getCartItems();
    }
}
