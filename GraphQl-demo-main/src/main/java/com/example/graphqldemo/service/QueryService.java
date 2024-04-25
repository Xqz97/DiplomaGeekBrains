package com.example.graphqldemo.service;

import com.example.graphqldemo.model.Author;
import com.example.graphqldemo.model.Book;
import com.example.graphqldemo.repository.AuthorRepository;
import com.example.graphqldemo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для выполнения запросов.
 * Автор: Турусов Роман
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class QueryService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    /**
     * Получить автора по имени.
     *
     * @param name Имя автора.
     * @return Объект автора.
     */
    public Author getAuthor(String name){
        Author author = authorRepository.findByName(name).orElseThrow();
        log.debug("author: {}", author);
        return author;
    }

    /**
     * Получить список книг по имени автора.
     *
     * @param authorName Имя автора.
     * @return Список книг, написанных данным автором.
     */

    public List<Book> getBooksByAuthor(String authorName){
        List<Book> books = bookRepository.getBooksByAuthorName(authorName);
        log.debug("books: {}", books);
        return books;
    }

    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Книга с идентификатором " + bookId + " не найдена"));
    }

    /**
     * Получить список всех книг.
     *
     * @return Список всех книг.
     */
    public List<Book> getAllBooks(){
        List<Book> books = bookRepository.findAll();
        log.debug("books: {}", books);
        return books;
    }

}
