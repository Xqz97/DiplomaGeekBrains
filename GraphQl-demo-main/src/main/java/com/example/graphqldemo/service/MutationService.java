package com.example.graphqldemo.service;

import com.example.graphqldemo.model.Author;
import com.example.graphqldemo.model.Book;
import com.example.graphqldemo.repository.AuthorRepository;
import com.example.graphqldemo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для мутаций (изменений).
 * @author Turusov Roman
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MutationService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    /**
     * Сохранить информацию об авторе и его книгах.
     *
     * @param name       Имя автора.
     * @param bookTitles Список названий книг.
     * @return Сохраненный объект автора.
     */
    public Author saveAuthor(String name, List<String> bookTitles) {
        log.debug("name: {}, bookIds: {}", name, bookTitles);
        Author author = new Author().setName(name);

        if (bookTitles != null && !bookTitles.isEmpty()){
            List<Book> books = bookTitles.stream()
                    .map(title -> bookRepository.findByTitle(title)
                            .orElse(bookRepository.save(new Book().setTitle(title))))
                    .collect(Collectors.toList());
            author.setBooks(books);
        }

        log.debug("author: {}", author);
        return authorRepository.save(author);
    }

    /**
     * Сохранить информацию о книге и ее авторах.
     *
     * @param title       Название книги.
     * @param authorNames Список имен авторов.
     * @return Сохраненный объект книги.
     */
    public Book saveBook(String title, List<String> authorNames) {
        log.debug("title: {}, authorIds: {}", title, authorNames);
        Book book = new Book().setTitle(title);

        if (authorNames != null && !authorNames.isEmpty()){
            List<Author> authors = authorNames.stream()
                    .map(name -> authorRepository.findByName(name)
                            .orElse(authorRepository.save(new Author().setName(name))))
                    .collect(Collectors.toList());
            book.setAuthors(authors);
        }
        log.debug("book: {}", book);
        checkBookDuplicate(book);
        return bookRepository.save(book);
    }


    /**
     * Добавить книгу к автору.
     *
     * @param authorId   Идентификатор автора.
     * @param bookTitle  Название книги.
     */
    public void addBookToAuthor(Long authorId, String bookTitle) {
        Author author = authorRepository.findById(authorId).orElseThrow();
        Book book = bookRepository.findByTitle(bookTitle).orElseGet(() -> bookRepository.save(new Book().setTitle(bookTitle)));

        List<Book> authorBooks = author.getBooks();
        authorBooks.add(book);
        author.setBooks(authorBooks);
        authorRepository.save(author);
    }

    /**
     * Добавить автора к книге.
     *
     * @param bookId     Идентификатор книги.
     * @param authorName Имя автора.
     */
    public void addAuthorToBook(Long bookId, String authorName) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        Author author = authorRepository.findByName(authorName).orElseGet(() -> authorRepository.save(new Author().setName(authorName)));

        List<Author> bookAuthors = book.getAuthors();
        bookAuthors.add(author);
        book.setAuthors(bookAuthors);
        bookRepository.save(book);
    }

    /**
     * Удалить книгу у автора.
     *
     * @param authorId Идентификатор автора.
     * @param bookId   Идентификатор книги.
     */
    public void removeBookFromAuthor(Long authorId, Long bookId) {
        Author author = authorRepository.findById(authorId).orElseThrow();

        List<Book> authorBooks = author.getBooks();
        authorBooks.removeIf(book -> book.getId().equals(bookId));
        author.setBooks(authorBooks);
        authorRepository.save(author);
    }

    /**
     * Удалить автора со всеми его книгами.
     *
     * @param authorId Идентификатор автора.
     */
    public void deleteAuthorWithBooks(Long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow();

        for (Book book : author.getBooks()) {
            if (book.getAuthors() != null) {
                book.getAuthors().remove(author);
                if (book.getAuthors().isEmpty()) {
                    bookRepository.delete(book);
                }
            }
        }
        author.setBooks(new ArrayList<>());
        authorRepository.save(author);
        authorRepository.delete(author);
    }

    /**
     * Обновить имя автора.
     *
     * @param authorId Идентификатор автора.
     * @param newName  Новое имя автора.
     */
    public void updateAuthorName(Long authorId, String newName) {
        Author author = authorRepository.findById(authorId).orElseThrow();
        author.setName(newName);
        authorRepository.save(author);
    }



    /**
     * Обновить название книги.
     *
     * @param bookId   Идентификатор книги.
     * @param newTitle Новое название книги.
     */
    public void updateBookTitle(Long bookId, String newTitle) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        book.setTitle(newTitle);
        bookRepository.save(book);
    }

    /**
     * Проверить наличие дубликатов книги.
     *
     * @param book Объект книги.
     */
    private void checkBookDuplicate(Book book) {
        if (!bookRepository.findByTitle(book.getTitle()).isEmpty() && bookRepository.findByTitle(book.getTitle()).get().equals(book)){
        }
    }
}
