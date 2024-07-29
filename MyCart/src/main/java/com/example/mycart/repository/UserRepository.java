package com.example.mycart.repository;

import com.example.mycart.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends SoftDeletesRepository<User,Long> {
    Optional<User> findByName(String name);
}
