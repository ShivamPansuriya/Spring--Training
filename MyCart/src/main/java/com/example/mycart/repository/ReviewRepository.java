package com.example.mycart.repository;

import com.example.mycart.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends BaseRepository<Review,Long>
{
    Page<Review> findByProductId(Long productId, Pageable page);

    Page<Review> findByUserId(Long userId, Pageable page);

    @Query(value = "SELECT r FROM Review r WHERE r.productId = :productId ORDER BY r.updatedTime DESC LIMIT :limit")
    List<Review> findLatestReviewsForProduct(@Param("productId") Long productId, @Param("limit") int limit);
}
