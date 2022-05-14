package com.example.bms.controller;

import com.example.bms.domain.Book;
import com.example.bms.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping(value = "/books")
    public String list(Model model){
        System.out.println("*** books mapping ***");
        List<Book> books = bookService.findBooks();
        model.addAttribute("books", books);
        return "books/bookList";
    }

    @GetMapping(value = "/books/new")
    public String createForm(){
        return "books/inputBookForm";
    }

    @PostMapping(value = "/books/new")
    public String create(BookForm form){
        Book book = new Book();
        book.setName(form.getName());
        book.setPublisher(form.getPublisher());
        book.setPrice(form.getPrice());
        bookService.addBook(book);
        return "redirect:/";
    }

    @GetMapping(value = "/books/search")
    public String searchForm(){
        return "books/searchBookForm";
    }

    @PostMapping(value = "/books/search")
    public String search(BookForm form, Model model){
        List<Book> books = bookService.findCondBooks(form);
        model.addAttribute("books", books);
        return "books/bookList";
    }
}
