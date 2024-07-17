package com.example.mycart.repository;

import com.example.mycart.model.Review;
import com.example.mycart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long>
{
    List<Review> findByProductId(Long productId);

    List<Review> findByUserId(Long userId);

    @Query(value = "SELECT r FROM Review r WHERE r.product.id = :productId ORDER BY r.reviewDate DESC LIMIT :limit")
    List<Review> findLatestReviewsForProduct(@Param("productId") Long productId, @Param("limit") int limit);
}
