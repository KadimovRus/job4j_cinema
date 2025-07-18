package ru.job4j.cinemajob.service.impl;

import org.springframework.stereotype.Service;
import ru.job4j.cinemajob.dto.FilmDto;
import ru.job4j.cinemajob.repository.FilmRepository;
import ru.job4j.cinemajob.service.FilmService;

import java.util.Collection;
import java.util.Optional;

@Service
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public Collection<FilmDto> findAll() {
        return filmRepository.findAll();
    }

    @Override
    public Optional<FilmDto> findById(int id) {
        return filmRepository.findById(id);
    }
}
