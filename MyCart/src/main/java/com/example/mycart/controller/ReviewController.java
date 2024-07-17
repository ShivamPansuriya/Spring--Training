package com.example.mycart.controller;

import com.example.mycart.payloads.ReviewDTO;
import com.example.mycart.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.mycart.constants.Constants.REVIEW_LIMIT;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController
{
    @Autowired
    private ReviewService service;

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long id)
    {
        var reviews = service.getReviewsById(id);

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByProductId(@PathVariable Long productId)
    {
        var reviews = service.getReviewsByProductId(productId);

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByUserId(@PathVariable Long userId)
    {
        var reviews = service.getReviewsByUserId(userId);

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping("/user/{userId}/product/{productId}")
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO review,
                                                  @PathVariable Long productId,
                                                  @PathVariable Long userId)
    {
        var createdReview = service.createReview(review,userId,productId);

        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long id,
                                                  @RequestBody ReviewDTO review)
    {
        var updatedReview = service.updateReview(review,id);

        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReviewDTO> deleteReview(@PathVariable Long id)
    {
        var review = service.deleteReview(id);

        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @GetMapping("/product/{productId}/latest")
    public ResponseEntity<List<ReviewDTO>> getLatestReviewsForProduct(
            @PathVariable Long productId,
            @RequestParam(defaultValue = REVIEW_LIMIT) int limit)
    {
        var latestReviews = service.getLatestReviewsForProduct(productId, limit);

        return new ResponseEntity<>(latestReviews, HttpStatus.OK);
    }

}
