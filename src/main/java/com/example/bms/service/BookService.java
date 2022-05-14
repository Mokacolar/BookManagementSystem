package com.example.bms.service;

import com.example.bms.Repository.BookRepository;
import com.example.bms.Repository.JdbcBookRepository;
import com.example.bms.controller.BookForm;
import com.example.bms.domain.Book;

import java.util.List;

public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findBooks(){
        return bookRepository.findAll();
    }

    public Long addBook(Book book){
        bookRepository.save(book);
        return book.getId();
    }

    public List<Book> findCondBooks(BookForm form){
        return bookRepository.findCond(form);
    }

}
