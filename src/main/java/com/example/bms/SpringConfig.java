package com.example.bms;

import com.example.bms.Repository.BookRepository;
import com.example.bms.Repository.JdbcBookRepository;
import com.example.bms.service.BookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public BookService bookService(){
        return new BookService(bookRepository());
    }

    @Bean
    public BookRepository bookRepository() {
        return new JdbcBookRepository(dataSource);
    }
}
