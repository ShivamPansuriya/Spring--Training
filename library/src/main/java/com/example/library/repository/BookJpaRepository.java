package com.example.library.repository;

import com.example.library.model.Book;
import com.example.library.model.Borrowing;
import com.example.library.model.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Join;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public class BookJpaRepository {

    @Autowired
    private EntityManager entityManager;

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void test(int i)
    {
        if(i==7)
        {
            throw new RuntimeException("testing");
        }
        System.out.println(i);
    }
}
