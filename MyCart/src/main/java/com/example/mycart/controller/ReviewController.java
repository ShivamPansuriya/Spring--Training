package com.example.mycart.controller;

import com.example.mycart.exception.ResourceNotFoundException;
import com.example.mycart.model.Review;
import com.example.mycart.modelmapper.EntityMapper;
import com.example.mycart.modelmapper.ReviewMapper;
import com.example.mycart.payloads.ReviewDTO;
import com.example.mycart.service.ReviewService;
import com.example.mycart.service.GenericService;
import com.example.mycart.utils.Validator;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.mycart.constants.Constants.ID;
import static com.example.mycart.constants.Constants.REVIEW_LIMIT;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController extends AbstractGenericController<Review,ReviewDTO,Long>
{
    private final ReviewService service;

    private final ReviewMapper<Review,ReviewDTO> mapper;

    private final Validator validator;

    public ReviewController(ReviewService service, ReviewMapper<Review, ReviewDTO> mapper, Validator validator) {
        this.service = service;
        this.mapper = mapper;
        this.validator = validator;
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Page<ReviewDTO>> getReviewsByProductId(@PathVariable Long productId,
                                                                 @RequestParam(defaultValue = "0",required = false) int pageNo)
    {
        if(validator.validateProduct(productId))
        {
            throw new ResourceNotFoundException("Product",ID,productId);
        }

        var reviews = service.getReviewsByProductId(productId,pageNo);

        return new ResponseEntity<>(mapper.toDTOs(reviews,pageNo), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<ReviewDTO>> getReviewsByUserId(@PathVariable Long userId,
                                                              @RequestParam(defaultValue = "0",required = false) int pageNo)
    {
        if(validator.validateUser(userId))
        {
            throw new ResourceNotFoundException("User","id",userId);
        }

        var reviews = service.getReviewsByUserId(userId,pageNo);

        return new ResponseEntity<>(mapper.toDTOs(reviews,pageNo), HttpStatus.OK);
    }

    @PostMapping("/user/{userId}/product/{productId}")
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO review,
                                                  @PathVariable Long productId,
                                                  @PathVariable Long userId
                                                  )
    {
        if(validator.validateUser(userId))
        {
            throw new ResourceNotFoundException("User","id",userId);
        }
        if(validator.validateProduct(productId))
        {
            throw new ResourceNotFoundException("Product","id",productId);
        }

        var createdReview = service.create(mapper.toEntity(review),userId,productId);

        return new ResponseEntity<>(mapper.toDTO(createdReview,0), HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}/latest")
    public ResponseEntity<List<ReviewDTO>> getLatestReviewsForProduct(
            @PathVariable Long productId,
            @RequestParam(defaultValue = REVIEW_LIMIT) int limit,
            @RequestParam(defaultValue = "0",required = false) int pageNo)
    {
        if(validator.validateProduct(productId))
        {
            throw new ResourceNotFoundException("Product","id",productId);
        }

        var latestReviews = service.getLatestReviewsForProduct(productId, limit);

        return new ResponseEntity<>(latestReviews.stream().map(review -> mapper.toDTO(review,pageNo)).toList(), HttpStatus.OK);
    }

    @Override
    protected EntityMapper<Review, ReviewDTO> getMapper() {
        return mapper;
    }

    @Override
    protected GenericService<Review,ReviewDTO, Long> getService() {
        return service;
    }
}
