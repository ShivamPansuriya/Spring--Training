package com.example.mycart.modelmapper;

import com.example.mycart.model.CartItem;
import com.example.mycart.payloads.CartItemDTO;
import com.example.mycart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapperImpl extends CartItemMapper<CartItem,CartItemDTO>
{

    @Autowired
    private ProductService productService;

    @Override
    protected ProductService getProductService() {
        return productService;
    }

    @Override
    public CartItemDTO toDTO(CartItem entity, int pageNo) {
        if(entity == null)
        {
            return null;
        }
        var dto = mapToBaseDTO(entity,new CartItemDTO());

        dto.setQuantity(entity.getQuantity());

        dto.setProductName(getProductsNames(entity.getProductId()));

        return dto;
    }

    @Override
    public CartItem toEntity(CartItemDTO dto) {
        if(dto == null)
        {
            return null;
        }
        var entity = new CartItem();
        entity.setQuantity(dto.getQuantity());
        return entity;
    }

}
