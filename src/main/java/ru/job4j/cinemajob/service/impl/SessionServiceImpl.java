package ru.job4j.cinemajob.service.impl;

import org.springframework.stereotype.Service;
import ru.job4j.cinemajob.dto.FilmSessionDto;
import ru.job4j.cinemajob.repository.impl.Sql2oFilmSessionRepository;
import ru.job4j.cinemajob.service.SessionService;

import java.util.Collection;
import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService {

    private final Sql2oFilmSessionRepository sql2oFilmSessionRepository;

    public SessionServiceImpl(Sql2oFilmSessionRepository sql2oFilmSessionRepository) {
        this.sql2oFilmSessionRepository = sql2oFilmSessionRepository;
    }

    @Override
    public Optional<FilmSessionDto> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Collection<FilmSessionDto> findAll() {
        return sql2oFilmSessionRepository.findAll();
    }
}
