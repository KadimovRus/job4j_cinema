package ru.job4j.cinemajob.converter;

import ru.job4j.cinemajob.dto.FilmSessionDto;
import ru.job4j.cinemajob.model.Film;
import ru.job4j.cinemajob.model.FilmSession;
import ru.job4j.cinemajob.model.Hall;

public class FilmSessionConverter {

    public static FilmSessionDto convertFilmSessionToFilmSessionDto(FilmSession filmSession,
                                                      Film film, Hall hall) {
        return new FilmSessionDto(filmSession.getId(),
                film,
                hall,
                filmSession.getStartTime(),
                filmSession.getEndTime(),
                filmSession.getPrice());
    }
}
