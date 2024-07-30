package com.example.mycart.modelmapper;

import com.example.mycart.model.Review;
import com.example.mycart.model.User;
import com.example.mycart.payloads.ReviewDTO;
import com.example.mycart.payloads.UserDTO;
import com.example.mycart.service.OrderService;
import com.example.mycart.service.ReviewService;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl extends UserMapper<User, UserDTO>
{

    private final OrderService orderService;

    private final ReviewService reviewService;

    private final ReviewMapper<Review, ReviewDTO> reviewMapper;

    public UserMapperImpl(OrderService orderService, ReviewService reviewService, ReviewMapper<Review, ReviewDTO> reviewMapper) {
        this.orderService = orderService;
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
    }

    @Override
    protected OrderService getOrderService() {
        return orderService;
    }

    @Override
    public UserDTO toDTO(User entity, int pageNo) {
        if(entity == null)
        {
            return null;
        }
        var dto = mapToBaseDTO(entity,new UserDTO());

        dto.setAddress(entity.getAddress());

        dto.setEmail(entity.getEmail());

        dto.setOrders(getOrdersId(entity.getId()));

        dto.setReviews(reviewMapper.toDTOs(reviewService.getReviewsByUserId(entity.getId(),pageNo),0));

        return dto;
    }

    @Override
    public User toEntity(UserDTO dto) {
        if(dto == null)
        {
            return null;
        }
        var entity = mapToBaseEntity(dto,new User());
        entity.setEmail(dto.getEmail());
        entity.setAddress(dto.getAddress());
        return entity;
    }
}
