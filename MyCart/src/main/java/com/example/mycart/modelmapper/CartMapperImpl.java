package com.example.mycart.modelmapper;

import com.example.mycart.model.Cart;
import com.example.mycart.model.CartItem;
import com.example.mycart.payloads.CartDTO;
import com.example.mycart.payloads.CartItemDTO;
import com.example.mycart.service.CartService;
import org.springframework.stereotype.Component;

@Component
public class CartMapperImpl extends CartMapper<Cart,CartDTO>
{
    final CartItemMapper<CartItem,CartItemDTO> cartItemMapper;

    final CartService service;

    public CartMapperImpl(CartItemMapper<CartItem, CartItemDTO> cartItemMapper, CartService service) {
        this.cartItemMapper = cartItemMapper;
        this.service = service;
    }

    @Override
    public CartDTO toDTO(Cart entity, int pageNo) {
        if(entity == null)
        {
            return null;
        }
        var dto = mapToBaseDTO(entity,new CartDTO());


        dto.setCartItems(service.getCartItems(entity.getUserId())
                .stream()
                .map(item-> cartItemMapper.toDTO(item,0))
                .toList()
        );


        dto.setUserId(entity.getUserId());

        return dto;
    }

    @Override
    public Cart toEntity(CartDTO dto) {
        if(dto == null)
        {
            return null;
        }
        return new Cart();
    }
}
