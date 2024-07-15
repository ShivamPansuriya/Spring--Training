package com.example.library.repository;


import com.example.library.model.Borrowing;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowRepository extends JpaRepository<Borrowing,Long> {

}
