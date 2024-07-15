package com.example.mycart.repository;

import com.example.mycart.model.Category;
import com.example.mycart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
