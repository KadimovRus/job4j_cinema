package ru.job4j.cinemajob.repository;

import ru.job4j.cinemajob.dto.FilmDto;
import ru.job4j.cinemajob.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmRepository {

    Film save(Film film);

    Optional<FilmDto> findById(int id);

    Collection<FilmDto> findAll();

    boolean deleteById(int id);

    boolean update(Film film);
}
