package com.example.library.controllers;

import com.example.library.payloads.ApiResponse;
import com.example.library.payloads.BookDTO;
import com.example.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> getAllBooks()
    {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @PostMapping("/books")
    public ResponseEntity<BookDTO> addBook(@RequestBody @Valid BookDTO bookDTO)
    {
        return new ResponseEntity<>(bookService.addBook(bookDTO),HttpStatus.CREATED);
    }

    @GetMapping("/test/")
    public void test()
    {
        bookService.test();
    }

    @GetMapping("/books/{isbn}")
    @Cacheable(cacheNames = "book", key = "#isbn")
    @ResponseStatus(HttpStatus.OK)
    public BookDTO getBookByIsbn(@PathVariable String isbn)
    {
        var res = bookService.getBookByIsbn(isbn);
        return res;
    }
}
