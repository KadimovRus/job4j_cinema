package ru.job4j.cinemajob.service;

import ru.job4j.cinemajob.dto.FilmDto;

import java.util.Collection;
import java.util.Optional;

public interface FilmService {

    Collection<FilmDto> findAll();

    Optional<FilmDto> findById(int id);

}
