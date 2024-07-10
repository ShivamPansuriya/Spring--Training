package com.example.library.service;

import com.example.library.exceptions.ResourceNotFoundException;
import com.example.library.model.Book;
import com.example.library.payloads.BookDTO;
import com.example.library.repository.BookJpaRepository;
import com.example.library.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookJpaRepository bookJpaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<BookDTO> getAllBooks() {
        var books =  bookRepository.findAll();

        return books.stream().map(book -> modelMapper.map(book, BookDTO.class)).toList();
    }

    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        var book = modelMapper.map(bookDTO,Book.class);
        var savedBook = bookRepository.save(book);
        return modelMapper.map(savedBook, BookDTO.class);
    }

    @Transactional
    public void test(){
        for(int i=0; i<10; ++i)
        {
            try {

                bookJpaRepository.test(i);
            }catch (RuntimeException e)
            {
                System.out.println("exception");
            }
        }
    }

    @Override
    public BookDTO getBookByIsbn(String isbn) {
        var book = bookRepository.findBookByIsbnEquals(isbn);
        if(book.isEmpty())
        {
            throw new ResourceNotFoundException("book","isbn",isbn);
        }
        return modelMapper.map(book.get(),BookDTO.class);
    }
}
