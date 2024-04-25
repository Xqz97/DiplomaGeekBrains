package com.example.graphqldemo.repository;

import com.example.graphqldemo.model.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * BookRepository.
 *
 * Этот интерфейс представляет собой репозиторий для управления экземплярами класса Book. Он расширяет интерфейс JpaRepository,
 * предоставляемый Spring Data JPA, который отвечает за основные операции CRUD над сущностями Book.
 *
 * @author Turusov Roman
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Получает список книг по имени автора с загрузкой авторов в одном запросе.
     *
     * @param authorName Имя автора, книги которого необходимо найти.
     * @return Список книг выбранного автора с загруженными авторами.
     */
    @Query("SELECT b FROM Book b JOIN fetch b.authors a WHERE a.name = :authorName")
    List<Book> getBooksByAuthorName(@Param("authorName") String authorName);

    /**
     * Находит книгу по названию.
     *
     * @param title Название книги, которую необходимо найти.
     * @return Optional, содержащий сущность Book, если найдена, или пустой, если не найдена.
     */
    Optional<Book> findByTitle(String title);

    /**
     * Извлекает все книги со всеми связанными авторами в одном запросе.
     *
     * @return Список книг с загруженными авторами.
     */
    @EntityGraph(attributePaths = {"authors"})
    List<Book> findAll();

}
