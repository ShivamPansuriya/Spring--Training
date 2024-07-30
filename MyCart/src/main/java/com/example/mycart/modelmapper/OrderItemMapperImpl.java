package com.example.mycart.modelmapper;

import com.example.mycart.model.OrderItems;
import com.example.mycart.payloads.OrderItemDTO;
import com.example.mycart.service.ProductService;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapperImpl extends OrderItemMapper<OrderItems, OrderItemDTO>
{

    private final ProductService productService;

    public OrderItemMapperImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected ProductService getProductService() {
        return productService;
    }

    @Override
    public OrderItemDTO toDTO(OrderItems entity, int pageNo) {
        if(entity == null)
        {
            return null;
        }
        var dto = mapToBaseDTO(entity,new OrderItemDTO());

        dto.setQuantity(entity.getQuantity());

        dto.setProductName(getProductsNames(entity.getProductId()));

        dto.setPrice(entity.getPrice());

        return dto;
    }

    @Override
    public OrderItems toEntity(OrderItemDTO dto) {
        return new OrderItems();
    }
}
