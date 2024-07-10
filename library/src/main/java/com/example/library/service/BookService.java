package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.payloads.BookDTO;

import java.util.List;

public interface BookService {
    public List<BookDTO> getAllBooks();
    BookDTO addBook(BookDTO bookDTO);
    void test();
    BookDTO getBookByIsbn(String isbn);
}
