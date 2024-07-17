package com.example.mycart.repository;

import com.example.mycart.model.Cart;
import com.example.mycart.model.CartItem;
import com.example.mycart.model.Product;
import com.example.mycart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long>
{
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

}
