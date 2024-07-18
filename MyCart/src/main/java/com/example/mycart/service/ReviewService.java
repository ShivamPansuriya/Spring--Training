package com.example.mycart.service;

import com.example.mycart.payloads.ReviewDTO;

import java.util.List;

public interface ReviewService
{
    List<ReviewDTO> getReviewsByProductId(Long productId);

    ReviewDTO getReviewsById(Long Id);

    List<ReviewDTO> getReviewsByUserId(Long userId);

    ReviewDTO createReview(ReviewDTO reviewDTO, Long userId, Long productId);

    ReviewDTO updateReview(Long id,ReviewDTO reviewDTO);

    ReviewDTO deleteReview(Long id);

    List<ReviewDTO> getLatestReviewsForProduct(Long productId, int limit);
}
