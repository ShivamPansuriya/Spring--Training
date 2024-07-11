package com.example.library.repository;

import com.example.library.model.Book;
import com.example.library.model.Borrowing;
import com.example.library.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRepository extends JpaRepository<Borrowing,Long> {
    List<Borrowing> findBorrowingsByMember(Member member);
}
