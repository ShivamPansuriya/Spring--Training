package com.example.mycart.modelmapper;

import com.example.mycart.model.Review;
import com.example.mycart.payloads.ReviewDTO;
import com.example.mycart.service.ProductService;
import com.example.mycart.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapperImpl extends ReviewMapper<Review, ReviewDTO>
{
    private final ProductService productService;

    private final UserService userService;

    public ReviewMapperImpl(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @Override
    protected ProductService getProductService() {
        return productService;
    }

    @Override
    protected UserService getUserService() {
        return userService;
    }

    @Override
    public ReviewDTO toDTO(Review entity, int pageNo) {
        if(entity == null)
        {
            return null;
        }
        var dto = mapToBaseDTO(entity,new ReviewDTO());
        dto.setComment(entity.getComment());
        dto.setReviewDate(entity.getUpdatedTime());
        dto.setProductName(getProductName(entity.getProductId()));
        dto.setRating(entity.getRatings());
        dto.setUserName(getUserName(entity.getUserId()));
        return dto;
    }

    @Override
    public Review toEntity(ReviewDTO dto) {
        if(dto == null)
        {
            return null;
        }
        var entity = new Review();
        entity.setComment(dto.getComment());
        entity.setRatings(dto.getRating());
        return entity;
    }
}
