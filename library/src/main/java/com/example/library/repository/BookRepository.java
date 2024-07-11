package com.example.library.repository;

import com.example.library.model.Book;
import com.example.library.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findBookByIsbnEquals(String isbn);

    List<Book> findBookByBorrowingMember(List<Member> members);
}
