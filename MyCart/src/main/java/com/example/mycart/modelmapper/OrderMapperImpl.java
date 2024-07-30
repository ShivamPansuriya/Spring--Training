package com.example.mycart.modelmapper;

import com.example.mycart.model.Order;
import com.example.mycart.model.OrderItems;
import com.example.mycart.payloads.OrderDTO;
import com.example.mycart.payloads.OrderItemDTO;
import com.example.mycart.service.OrderService;
import com.example.mycart.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class OrderMapperImpl extends OrderMapper<Order, OrderDTO>
{
    private final OrderItemMapper<OrderItems, OrderItemDTO> orderItemMapper;

    private final OrderService orderService;

    private final UserService userService;

    public OrderMapperImpl(OrderItemMapper<OrderItems, OrderItemDTO> orderItemMapper, OrderService orderService, UserService userService) {
        this.orderItemMapper = orderItemMapper;
        this.orderService = orderService;
        this.userService = userService;
    }

    @Override
    protected UserService getUserService() {
        return userService;
    }

    @Override
    public OrderDTO toDTO(Order entity, int pageNo) {
        if(entity == null)
        {
            return null;
        }
        var dto = mapToBaseDTO(entity,new OrderDTO());
        dto.setOrderDate(entity.getOrderDate());
        dto.setStatus(entity.getStatus());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setOrderItems(orderService.findOrderItemsByOrder(entity.getId(),pageNo)
                .map(item->orderItemMapper.toDTO(item,pageNo)));
        dto.setUserName(getUserName(entity.getUserId()));
        return dto;
    }

    @Override
    public Order toEntity(OrderDTO dto) {
        if(dto == null)
        {
            return null;
        }
        return new Order();
    }
}
