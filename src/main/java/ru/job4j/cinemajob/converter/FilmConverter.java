package ru.job4j.cinemajob.converter;

import org.springframework.stereotype.Service;
import ru.job4j.cinemajob.dto.FilmDto;
import ru.job4j.cinemajob.model.Film;
import ru.job4j.cinemajob.model.Genre;

@Service
public class FilmConverter {

    public static FilmDto convertFilmToFilmDto(Film film, Genre genre) {
        return new FilmDto(film.getId(),
                film.getName(),
                film.getDescription(),
                film.getReleaseYear(),
                genre,
                film.getMinimalAge(),
                film.getDurationInMinutes());
    }
}
