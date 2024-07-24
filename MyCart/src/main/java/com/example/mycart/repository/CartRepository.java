package com.example.mycart.repository;

import com.example.mycart.model.Cart;
import com.example.mycart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends BaseRepository<Cart,Long>
{
    Optional<Cart> findByUserId(Long userId);

}
