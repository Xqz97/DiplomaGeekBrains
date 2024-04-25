package com.example.graphqldemo.repository;

import com.example.graphqldemo.model.Author;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * AuthorRepository.
 *
 * Этот интерфейс представляет собой репозиторий для управления экземплярами класса Author. Он расширяет интерфейс JpaRepository,
 * предоставляемый Spring Data JPA, который отвечает за основные операции CRUD над сущностями Author.
 *
 * @author Turusov Roman
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {

    /**
     * Находит сущность автора по имени вместе со связанными книгами в одном запросе, чтобы избежать проблемы N+1 запросов.
     *
     * @param name Имя автора, которого необходимо найти.
     * @return Optional, содержащий сущность Author, если найдена, или пустой, если не найдена.
     */
    @EntityGraph(attributePaths = {"books"})
    Optional<Author> findByName(String name);

    /**
     * Извлекает все сущности авторов вместе со связанными книгами в одном запросе, чтобы избежать проблемы N+1 запросов.
     *
     * @return Список сущностей Author с их книгами.
     */
    @EntityGraph(attributePaths = {"books"})
    List<Author> findAll();
}
