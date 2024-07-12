package com.example.library.repository;

import com.example.library.model.Book;
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

    public Long countBorrowingMatch(Member member, Book book)
    {
        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(Long.class);
        var root = query.from(Borrowing.class);
        query.where(
                builder.and(
                        builder.equal(root.get("member"),member),
                        builder.equal(root.get("book"),book)
                )
        );
        var result = entityManager.createQuery(query.select(builder.count(root)));
        return result.getSingleResult();
    }
}
