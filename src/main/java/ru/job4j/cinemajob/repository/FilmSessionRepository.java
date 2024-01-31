package ru.job4j.cinemajob.repository;

import ru.job4j.cinemajob.dto.FilmSessionDto;
import ru.job4j.cinemajob.model.FilmSession;

import java.util.Collection;
import java.util.Optional;

public interface FilmSessionRepository {

    FilmSession save(FilmSession filmSession);

    Optional<FilmSessionDto> findById(int id);

    Collection<FilmSessionDto> findAll();

    boolean deleteById(int id);

}
