package com.example.mycart.repository;


import com.example.mycart.model.CartItem;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends BaseRepository<CartItem,Long>
{
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);
    List<CartItem> findCartItemsByCartId(Long id);
}
