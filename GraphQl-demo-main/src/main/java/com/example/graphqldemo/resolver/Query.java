package com.example.graphqldemo.resolver;

import com.example.graphqldemo.model.Author;
import com.example.graphqldemo.model.Book;
import com.example.graphqldemo.service.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Класс-контроллер для выполнения запросов.
 * @author Turusov Roman
 */
@Controller
@RequiredArgsConstructor
public class Query {
    private final QueryService service;

    /**
     * Получить автора по имени.
     *
     * @param name Имя автора.
     * @return Объект автора.
     */
    @QueryMapping
    public Author getAuthor(@Argument String name){
        return service.getAuthor(name);
    }

    /**
     * Получить список книг по имени автора.
     *
     * @param authorName Имя автора.
     * @return Список книг этого автора.
     */
    @QueryMapping
    public List<Book> getBooksByAuthor(@Argument String authorName){
        return service.getBooksByAuthor(authorName);
    }

    /**
     * Получить книгу по идентификатору.
     *
     * @param bookId Идентификатор книги.
     * @return Объект книги.
     */
    @QueryMapping
    public Book getBookById(@Argument Long bookId) {
        return service.getBookById(bookId);
    }

    /**
     * Получить список всех книг.
     *
     * @return Список всех книг.
     */
    @QueryMapping
    public List<Book> getAllBooks(){
        return service.getAllBooks();
    }
}
