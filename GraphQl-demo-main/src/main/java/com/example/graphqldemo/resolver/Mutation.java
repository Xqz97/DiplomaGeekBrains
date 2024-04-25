package com.example.graphqldemo.resolver;

import com.example.graphqldemo.model.Author;
import com.example.graphqldemo.model.Book;
import com.example.graphqldemo.service.MutationService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Класс Mutation.
 *
 * Управляет мутациями (изменениями) данных.
 *
 * @author Turusov Roman
 */
@Controller
@RequiredArgsConstructor
public class Mutation {
    private final MutationService service;

    /**
     * Сохраняет информацию об авторе в базу данных.
     *
     * @param name       Имя автора.
     * @param bookTitles Список названий книг автора.
     * @return Объект типа Author после сохранения.
     */
    @MutationMapping
    public Author saveAuthor(@Argument String name, @Argument List<String> bookTitles) {
        return service.saveAuthor(name, bookTitles);
    }

    /**
     * Сохраняет информацию о книге в базу данных.
     *
     * @param title       Название книги.
     * @param authorNames Список имен авторов книги.
     * @return Объект типа Book после сохранения.
     */
    @MutationMapping
    public Book saveBook(@Argument String title, @Argument List<String> authorNames) {
        return service.saveBook(title, authorNames);
    }


    /**
     * Добавляет книгу к автору в базе данных.
     *
     * @param authorId   Идентификатор автора.
     * @param bookTitle  Название книги.
     */
    @MutationMapping
    public void addBookToAuthor(@Argument Long authorId, @Argument String bookTitle) {
        service.addBookToAuthor(authorId, bookTitle);
    }

    /**
     * Добавляет автора к книге в базе данных.
     *
     * @param bookId     Идентификатор книги.
     * @param authorName Имя автора.
     */
    @MutationMapping
    public void addAuthorToBook(@Argument Long bookId, @Argument String authorName) {
        service.addAuthorToBook(bookId, authorName);
    }

    /**
     * Удаляет автора вместе с его книгами из базы данных.
     *
     * @param authorId Идентификатор автора.
     */
    @MutationMapping
    public void deleteAuthorWithBooks(@Argument Long authorId) {
        service.deleteAuthorWithBooks(authorId);
    }

    /**
     * Удаляет книгу у автора в базе данных.
     *
     * @param authorId Идентификатор автора.
     * @param bookId   Идентификатор книги.
     */
    @MutationMapping
    public void deleteBookFromAuthor(@Argument Long authorId, @Argument Long bookId) {
        service.removeBookFromAuthor(authorId, bookId);
    }

    /**
     * Обновляет имя автора в базе данных.
     *
     * @param authorId Идентификатор автора.
     * @param newName  Новое имя автора.
     */
    @MutationMapping
    public void updateAuthorName(@Argument Long authorId, @Argument String newName) {
        service.updateAuthorName(authorId, newName);
    }

    /**
     * Обновляет название книги в базе данных.
     *
     * @param bookId   Идентификатор книги.
     * @param newTitle Новое название книги.
     */
    @MutationMapping
    public void updateBookTitle(@Argument Long bookId, @Argument String newTitle) {
        service.updateBookTitle(bookId, newTitle);
    }

}
