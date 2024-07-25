package com.example.mycart.service;

import com.example.mycart.model.Review;
import com.example.mycart.payloads.ReviewDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewService extends GenericService<Review,ReviewDTO,Long>
{
    Page<Review> getReviewsByProductId(Long productId, int pageNo);

    Page<Review> getReviewsByUserId(Long userId, int pageNo);

    Review create(Review review, Long userId, Long productId);

    List<Review> getLatestReviewsForProduct(Long productId, int limit);
}
