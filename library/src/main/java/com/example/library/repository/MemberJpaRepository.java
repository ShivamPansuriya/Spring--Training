package com.example.library.repository;

import com.example.library.model.Borrowing;
import com.example.library.model.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberJpaRepository {

    @Autowired
    EntityManager entityManager;

    public List<Member> memberBorrowingMoreThanTwo(Long value)
    {
        var builder = entityManager.getCriteriaBuilder();

        var cq = builder.createQuery(Member.class);

        var memberRoot = cq.from(Member.class);

        var subQuery = cq.subquery(Long.class);

        var subRoot = subQuery.correlate(memberRoot);

        Join<Member, Borrowing> joinRoot = subRoot.join("borrowingList");

        subQuery.select(builder.count(joinRoot));

        cq.select(memberRoot)
                .where(builder.gt(subQuery,value));

        var result = entityManager.createQuery(cq);

        return result.getResultList();
    }
}
