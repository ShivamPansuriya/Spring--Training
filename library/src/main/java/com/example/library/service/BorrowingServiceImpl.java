package com.example.library.service;

import com.example.library.exceptions.ApiException;
import com.example.library.exceptions.ResourceNotFoundException;
import com.example.library.model.Book;
import com.example.library.model.Borrowing;
import com.example.library.payloads.BorrowingDTO;
import com.example.library.payloads.DetailDTO;
import com.example.library.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
@Service
public class BorrowingServiceImpl implements BorrowingService {

    @Autowired
    BorrowRepository borrowRepository;

    @Autowired
    BorrowJpaRepository borrowJpaRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookService bookService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    @Transactional
    public BorrowingDTO addBorrowing(String isbn, String memberId) {
        var borrowing = new Borrowing();
        Optional<Book> bookOptional = bookRepository.findBookByIsbnEquals(isbn);
        var memberOptional = memberRepository.findMemberByMemberId(memberId);
        if(bookOptional.isEmpty())
            throw new ResourceNotFoundException("Book","isbn",isbn);

        if(memberOptional.isEmpty())
            throw new ResourceNotFoundException("Member","memberId",memberId);

        var member = memberOptional.get();
        Book book = bookOptional.get();

        if(borrowJpaRepository.countBorrowingMatch(member,book) != 0)
            throw new ApiException("Book has already been issued by "+member.getName()+" on: "+borrowing.getBorrowedDate());

        book.addToBorrowingMember(member);
        member.addToBorrowedBook(book);

        borrowing.setBook(book);
        borrowing.setMember(member);
        borrowing.setBorrowedDate(new Date());

        var savedBorrow =borrowRepository.save(borrowing);
        return modelMapper.map(savedBorrow,BorrowingDTO.class);
    }

    @Transactional
    public List<DetailDTO> borrowedList()
    {
        var members =memberRepository.findAllByBooksBorrowedIsNotEmpty();
        return members.stream().map(member -> modelMapper.map(member, DetailDTO.class)).toList();
    }

    @Transactional()
    public BorrowingDTO returnBook(Long id)
    {
        var borrowingOptional = borrowRepository.findById(id);
        if(borrowingOptional.isEmpty())
            throw new ResourceNotFoundException("Borrowing", "id",id);
        var borrowing = borrowingOptional.get();

        if(borrowing.getReturnDate()!=null)
            throw new ApiException("Book has already been returned");
        var book = borrowing.getBook();
        var member = borrowing.getMember();
        book.removeFromBorrowingMember(member);
        member.removeFromBorrowedBook(book);
        borrowing.setReturnDate(new Date());

        var savedBorrowing = borrowRepository.save(borrowing);
        return modelMapper.map(savedBorrowing,BorrowingDTO.class);
    }
}
