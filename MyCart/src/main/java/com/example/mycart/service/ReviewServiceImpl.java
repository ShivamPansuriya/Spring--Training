package com.example.mycart.service;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.Review;
import com.example.mycart.payloads.ReviewDTO;
import com.example.mycart.repository.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService
{
    @Autowired
    private ReviewRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<ReviewDTO> getReviewsByProductId(Long productId) {
        var reviews = repository.findByProductId(productId);

        return reviews.stream()
                .map(review -> mapper.map(review,ReviewDTO.class))
                .toList();
    }

    @Override
    public ReviewDTO getReviewsById(Long id) {
        var review = findReviewById(id);
        return mapper.map(review,ReviewDTO.class);
    }

    @Override
    public List<ReviewDTO> getReviewsByUserId(Long userId) {
        var reviews = repository.findByUserId(userId);

        return reviews.stream()
                .map(review -> mapper.map(review,ReviewDTO.class))
                .toList();
    }

    public Review findReviewById(Long id)
    {
        return repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Review","id",id));
    }
    @Override
    @Transactional
    public ReviewDTO createReview(ReviewDTO reviewDTO, Long userId, Long productId) {
        var review = mapper.map(reviewDTO, Review.class);
        review.setUser(userService.findUserById(userId));

        review.setProduct(productService.findByProductId(productId));

        review.setReviewDate(LocalDateTime.now());

        var savedReview = repository.save(review);

        return mapper.map(savedReview,ReviewDTO.class);
    }

    @Override
    public ReviewDTO updateReview(ReviewDTO reviewDTO, Long id)
    {
        var review = findReviewById(id);
        review.setReviewDate(LocalDateTime.now());
        review.setComment(reviewDTO.getComment());
        review.setRatings(reviewDTO.getRatings());
        var updatedReview = repository.save(review);
        return mapper.map(updatedReview,ReviewDTO.class);
    }

    @Override
    public ReviewDTO deleteReview(Long id) {
        var review = findReviewById(id);
        repository.delete(review);
        return mapper.map(review,ReviewDTO.class);
    }

    @Override
    public List<ReviewDTO> getLatestReviewsForProduct(Long productId, int limit) {
        var reviews = repository.findLatestReviewsForProduct(productId,limit);

        return reviews.stream()
                .map(review -> mapper.map(review,ReviewDTO.class))
                .toList();
    }
}
