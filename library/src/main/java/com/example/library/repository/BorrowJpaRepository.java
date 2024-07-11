package com.example.library.repository;

import com.example.library.model.Borrowing;
import com.example.library.model.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BorrowJpaRepository {

    @Autowired
    EntityManager entityManager;


}
