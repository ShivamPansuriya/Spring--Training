package com.example.restapi.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserDetails,Long> {
    List<UserDetails> findByUsername(String username);

    List<UserDetails> findByRole(String role);
}
