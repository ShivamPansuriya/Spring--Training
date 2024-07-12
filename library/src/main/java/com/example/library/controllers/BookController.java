package com.example.library.controllers;

import com.example.library.payloads.ApiResponse;
import com.example.library.payloads.BookDTO;
import com.example.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class BookController {

    private static final String DIRECTORY_PATH = "/home/shivam/Spring/library/src/main/resources/static/";

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
    @ResponseStatus(HttpStatus.OK)
    public BookDTO getBookByIsbn(@PathVariable String isbn)
    {
        return bookService.getBookByIsbn(isbn);

    }

    @PostMapping("/books/upload")
    public ResponseEntity<ApiResponse> addBook(@RequestParam("file")MultipartFile file) throws IOException
    {
        file.transferTo(new File(DIRECTORY_PATH + file.getOriginalFilename()));
        return new ResponseEntity<>(new ApiResponse("save success",true),HttpStatus.CREATED);
    }
}
