package ru.job4j.cinemajob.service;

import ru.job4j.cinemajob.dto.FilmSessionDto;

import java.util.Collection;
import java.util.Optional;

public interface SessionService {

    Optional<FilmSessionDto> findById(int id);

    Collection<FilmSessionDto> findAll();

}
