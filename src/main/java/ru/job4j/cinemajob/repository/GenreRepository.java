package ru.job4j.cinemajob.repository;

import ru.job4j.cinemajob.model.Genre;

import java.util.Collection;
import java.util.Optional;

public interface GenreRepository {

    Genre save(Genre genre);

    Optional<Genre> findById(int id);

    boolean deleteById(int id);
}
