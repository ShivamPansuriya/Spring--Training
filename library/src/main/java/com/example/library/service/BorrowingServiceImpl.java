package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.Borrowing;
import com.example.library.model.Member;
import com.example.library.payloads.BorrowingDTO;
import com.example.library.repository.BookRepository;
import com.example.library.repository.BorrowRepository;
import com.example.library.repository.MemberJpaRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BorrowingServiceImpl implements BorrowingService {

    @Autowired
    BorrowRepository borrowRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookService bookService;

    @Autowired
    MemberService memberService;

    @Autowired
    ModelMapper modelMapper;

    @Override
    @Transactional
    public BorrowingDTO addBorrowing(String isbn, String memberId) {
        var borrowing = new Borrowing();
        var book = modelMapper.map(bookService.getBookByIsbn(isbn), Book.class);
        var member = modelMapper.map(memberService.getMemberById(memberId), Member.class);

        borrowing.setBook(book);
        borrowing.setMember(member);
        borrowing.setBorrowedDate(new Date());

        var savedBorrow =borrowRepository.save(borrowing);
        return modelMapper.map(savedBorrow,BorrowingDTO.class);
    }

    @Transactional
    public void borrowedList()
    {
    }
}
