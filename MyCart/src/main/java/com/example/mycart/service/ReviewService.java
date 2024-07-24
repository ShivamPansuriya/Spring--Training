package com.example.mycart.service;

import com.example.mycart.model.Review;
import com.example.mycart.payloads.inheritDTO.ReviewDTO;

import java.util.List;

public interface ReviewService extends GenericService<Review,ReviewDTO,Long>
{
    List<ReviewDTO> getReviewsByProductId(Long productId);

//    ReviewDTO getReviewsById(Long Id);

    List<ReviewDTO> getReviewsByUserId(Long userId);

    ReviewDTO create(ReviewDTO reviewDTO, Long userId, Long productId);

//    ReviewDTO updateReview(Long id,ReviewDTO reviewDTO);
//
//    ReviewDTO deleteReview(Long id);

    List<ReviewDTO> getLatestReviewsForProduct(Long productId, int limit);
}
