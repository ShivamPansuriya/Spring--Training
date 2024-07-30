package com.example.mycart.repository;


import com.example.mycart.model.CartItem;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends SoftDeletesRepository<CartItem,Long>
{
    Optional<CartItem> findByCartIdAndProductIdAndDeleted(Long cartId, Long productId, boolean isDeleted);
    List<CartItem> findCartItemsByCartIdAndDeleted(Long id,boolean isDeleted);
}
