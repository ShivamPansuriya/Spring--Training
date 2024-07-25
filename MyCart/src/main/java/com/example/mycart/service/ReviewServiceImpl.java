package com.example.mycart.service;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.Review;
import com.example.mycart.payloads.ReviewDTO;
import com.example.mycart.repository.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@CacheConfig(cacheNames = "mycache", keyGenerator = "customKeyGenerator")
public class ReviewServiceImpl extends AbstractGenericService<Review, ReviewDTO, Long> implements ReviewService
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
    public Page<Review> getReviewsByProductId(Long productId,int pageNo)
    {
        return repository.findByProductId(productId,  PageRequest.of(pageNo,10));
    }

    @Override
    public Page<Review> getReviewsByUserId(Long userId,int pageNo)
    {
        return repository.findByUserId(userId, PageRequest.of(pageNo,10));
    }

    public Review findReviewById(Long id)
    {
        return repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Review","id",id));
    }

    @Override
    @Transactional
    public Review create(Review review, Long userId, Long productId) {
        review.setUserId(userService.findUserById(userId).getId());

        review.setProductId(productService.findById(productId).getId());

        return repository.save(review);
    }

    @Override
    @CachePut
    public Review update( Long id,ReviewDTO reviewDTO)
    {
        var review = findReviewById(id);
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
    protected JpaRepository<Review, Long> getRepository() {
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
