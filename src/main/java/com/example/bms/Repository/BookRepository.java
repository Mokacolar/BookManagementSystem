package com.example.bms.Repository;

import com.example.bms.controller.BookForm;
import com.example.bms.domain.Book;

import java.util.List;

public interface BookRepository {

    List<Book> findAll();
    Book save (Book book);
    List<Book> findCond(BookForm form);
}
