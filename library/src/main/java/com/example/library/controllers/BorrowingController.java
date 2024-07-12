package com.example.library.controllers;

import com.example.library.model.Book;
import com.example.library.model.Borrowing;
import com.example.library.payloads.ApiResponse;
import com.example.library.payloads.BorrowingDTO;
import com.example.library.payloads.DetailDTO;
import com.example.library.payloads.MemberDTO;
import com.example.library.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class BorrowingController {

    @Autowired
    BorrowingService borrowingService;

    @PostMapping("/{isbn}/{memberId}/borrow/")
    public ResponseEntity<BorrowingDTO> addBorrowing(@PathVariable String isbn, @PathVariable String memberId)
    {
        return new ResponseEntity<>(borrowingService.addBorrowing(isbn, memberId), HttpStatus.CREATED);
    }

    @GetMapping("/borrow")
    public ResponseEntity<List<DetailDTO>> studentsWithBooksList()
    {
        return new ResponseEntity<>(borrowingService.borrowedList(), HttpStatus.OK);
    }

    @DeleteMapping("/borrow/{id}")
    public ResponseEntity<BorrowingDTO> returnBook(@PathVariable Long id)
    {
        return new ResponseEntity<>(borrowingService.returnBook(id),HttpStatus.OK);
    }
}
