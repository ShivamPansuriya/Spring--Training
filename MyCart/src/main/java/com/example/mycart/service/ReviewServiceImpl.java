package com.example.mycart.service;

import com.example.mycart.model.Review;
import com.example.mycart.payloads.ReviewDTO;
import com.example.mycart.repository.ReviewRepository;
import com.example.mycart.repository.SoftDeletesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewServiceImpl extends AbstractGenericService<Review, ReviewDTO, Long> implements ReviewService
{
    private final ReviewRepository repository;

    public ReviewServiceImpl(ReviewRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Review> getReviewsByProductId(Long productId,int pageNo)
    {
        return repository.findByProductId(productId,  PageRequest.of(pageNo,10));
    }

    @Override
    public Page<Review> getReviewsByUserId(Long userId,int pageNo)
    {
        return repository.findByUserId(userId, PageRequest.of(pageNo,10));
    }

    @Override
    @Transactional
    public Review create(Review review, Long userId, Long productId) {
        review.setUserId(userId);

        review.setProductId(productId);

        return repository.save(review);
    }

    @Override
    public Review update( Long id,ReviewDTO reviewDTO)
    {
        var review = findById(id);
        review.setComment(reviewDTO.getComment());
        review.setRatings(reviewDTO.getRating());
        return repository.save(review);
    }

    @Override
    public List<Review> getLatestReviewsForProduct(Long productId, int limit)
    {
        return repository.findLatestReviewsForProduct(productId,limit);
    }

    @Override
    protected SoftDeletesRepository<Review, Long> getRepository() {
        return repository;
    }

    @Override
    protected Class<Review> getEntityClass() {
        return Review.class;
    }

    @Override
    protected Class<ReviewDTO> getDtoClass() {
        return ReviewDTO.class;
    }
}
