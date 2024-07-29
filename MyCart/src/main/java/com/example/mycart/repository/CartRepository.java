package com.example.mycart.repository;

import com.example.mycart.model.Cart;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends SoftDeletesRepository<Cart,Long>
{
    Optional<Cart> findByUserIdAndDeleted(Long userId, boolean isDeleted);

}
