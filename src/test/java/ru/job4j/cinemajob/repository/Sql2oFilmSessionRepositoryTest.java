package ru.job4j.cinemajob.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinemajob.configuration.DatasourceConfiguration;
import ru.job4j.cinemajob.dto.FilmSessionDto;
import ru.job4j.cinemajob.model.File;
import ru.job4j.cinemajob.model.Film;
import ru.job4j.cinemajob.model.FilmSession;
import ru.job4j.cinemajob.model.Genre;
import ru.job4j.cinemajob.model.Hall;
import ru.job4j.cinemajob.repository.impl.Sql2oFileRepository;
import ru.job4j.cinemajob.repository.impl.Sql2oFilmRepository;
import ru.job4j.cinemajob.repository.impl.Sql2oFilmSessionRepository;
import ru.job4j.cinemajob.repository.impl.Sql2oGenreRepository;
import ru.job4j.cinemajob.repository.impl.Sql2oHallRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;

public class Sql2oFilmSessionRepositoryTest {

    public static Sql2oFilmSessionRepository sql2oFilmSessionRepository;
    public static Sql2oHallRepository sql2oHallRepository;
    public static Sql2oFilmRepository sql2oFilmRepository;
    private static Sql2oFileRepository sql2oFileRepository;
    private static Sql2oGenreRepository sql2oGenreRepository;
    private static File file;
    private static Genre genre;
    private static Hall hall;
    private static Film film;
    private final LocalDateTime startTime = LocalDateTime.of(2023, 12, 1, 12, 0);
    private final LocalDateTime endTime = LocalDateTime.of(2023, 12, 1, 14, 0);

    @BeforeAll
    public static void initRepository() throws IOException {
        var properties = new Properties();
        try (var inputStream = Sql2oUserRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFilmSessionRepository = new Sql2oFilmSessionRepository(sql2o);
        sql2oHallRepository = new Sql2oHallRepository(sql2o);
        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
        sql2oFileRepository = new Sql2oFileRepository(sql2o);
        sql2oGenreRepository = new Sql2oGenreRepository(sql2o);

        file = new File("test7", "test7");
        sql2oFileRepository.save(file);
        genre = sql2oGenreRepository.save(new Genre(7, "Comedy7"));
        hall = sql2oHallRepository.save(new Hall("hall", 3, 4, "description"));
        film = sql2oFilmRepository.save(new Film("name", "description", 2023, genre.getId(),
                12, 120, file.getId()));
    }

    @AfterAll
    public static void clear() {
        sql2oHallRepository.deleteById(hall.getId());
        sql2oFilmRepository.deleteById(film.getId());
        sql2oFileRepository.deleteById(file.getId());
        sql2oGenreRepository.deleteById(genre.getId());
    }

    @AfterEach
    public void clearFilmSessions() {
        Collection<FilmSessionDto> list = sql2oFilmSessionRepository.findAll();
        for (FilmSessionDto filmSessionDto : list) {
            sql2oFilmSessionRepository.deleteById(filmSessionDto.getId());
        }
    }

    @Test
    public void whenSaveThenGetSame() {
        var filmSession = sql2oFilmSessionRepository.save(new FilmSession(film.getId(), hall.getId(), startTime, endTime, 200));
        var savedFilmSession = sql2oFilmSessionRepository.findById(filmSession.getId());
        assertThat(savedFilmSession.get()).isEqualTo(new FilmSessionDto(filmSession, film, hall));
    }

    @Test
    public void whenSaveSeveralThenGetAll() {
        var filmSession1 = sql2oFilmSessionRepository.save(new FilmSession(film.getId(), hall.getId(), startTime, endTime, 200));
        var filmSession2 = sql2oFilmSessionRepository.save(new FilmSession(film.getId(), hall.getId(), startTime.plusHours(2), endTime.plusHours(2), 200));
        var filmSession3 = sql2oFilmSessionRepository.save(new FilmSession(film.getId(), hall.getId(), startTime.plusHours(4), endTime.plusHours(4), 200));
        var result = sql2oFilmSessionRepository.findAll();
        assertThat(result).isEqualTo(List.of(new FilmSessionDto(filmSession1, film, hall),
                                             new FilmSessionDto(filmSession2, film, hall),
                                             new FilmSessionDto(filmSession3, film, hall)));
    }

    @Test
    public void whenDontSaveThenNothingFound() {
        assertThat(sql2oFilmSessionRepository.findAll()).isEqualTo(emptyList());
        assertThat(sql2oFilmSessionRepository.findById(0)).isEqualTo(empty());
    }

    @Test
    public void whenDeleteThenGetEmptyOptional() {
        var filmSession = sql2oFilmSessionRepository.save(new FilmSession(film.getId(), hall.getId(), startTime, endTime, 200));
        var isDeleted = sql2oFilmSessionRepository.deleteById(filmSession.getId());
        var savedFilmSessions = sql2oFilmSessionRepository.findById(filmSession.getId());
        assertThat(isDeleted).isTrue();
        assertThat(savedFilmSessions).isEqualTo(empty());
    }

    @Test
    public void whenDeleteByInvalidIdThenGetFalse() {
        assertThat(sql2oFilmSessionRepository.deleteById(1)).isFalse();
    }

}