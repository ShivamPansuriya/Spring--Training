package com.example.library.repository;


import com.example.library.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findMemberByMemberId(String memberId);

    List<Member> findAllByBooksBorrowedIsNotEmpty();
}
