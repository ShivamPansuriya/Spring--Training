package com.example.library.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

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
