package ru.job4j.cinemajob.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinemajob.configuration.DatasourceConfiguration;
import ru.job4j.cinemajob.dto.FilmDto;
import ru.job4j.cinemajob.model.File;
import ru.job4j.cinemajob.model.Film;
import ru.job4j.cinemajob.model.Genre;
import ru.job4j.cinemajob.repository.impl.Sql2oFileRepository;
import ru.job4j.cinemajob.repository.impl.Sql2oFilmRepository;
import ru.job4j.cinemajob.repository.impl.Sql2oGenreRepository;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;

public class Sql2oFilmRepositoryTest {

    private static Sql2oFilmRepository sql2oFilmRepository;
    private static Sql2oFileRepository sql2oFileRepository;
    private static Sql2oGenreRepository sql2oGenreRepository;
    private static File file;
    private static Genre genre;

    @BeforeAll
    public static void initRepository() throws IOException {
        var properties = new Properties();
        try (var inputStream = Sql2oFilmRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }

        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
        sql2oFileRepository = new Sql2oFileRepository(sql2o);
        sql2oGenreRepository = new Sql2oGenreRepository(sql2o);

        file = new File("test", "test");
        sql2oFileRepository.save(file);
        genre = sql2oGenreRepository.save(new Genre(1, "Comedy"));
    }

    @AfterAll
    public static void deleteFileAndGenre() {
        sql2oFileRepository.deleteById(file.getId());
        sql2oGenreRepository.deleteById(genre.getId());
    }

    @AfterEach
    public void clear() {
        var films = sql2oFilmRepository.findAll();
        for (var film: films) {
            sql2oFilmRepository.deleteById(film.getId());
        }
    }

    @Test
    public void whenSaveThenGetSame() {
        var film = sql2oFilmRepository.save(new Film("name", "description", 2020, genre.getId(), 12, 120, file.getId()));
        var savedFilm = sql2oFilmRepository.findById(film.getId());
        assertThat(savedFilm.get())
                .usingRecursiveComparison()
                .isEqualTo(new FilmDto(film, genre));
    }

    @Test
    public void whenSaveSeveralThenGetAll() {
        var film1 = sql2oFilmRepository.save(new Film("name", "description", 2020, genre.getId(), 12, 120, file.getId()));
        var film2 = sql2oFilmRepository.save(new Film("name2", "description2", 2022, genre.getId(), 11, 110, file.getId()));
        var film3 = sql2oFilmRepository.save(new Film("name3", "description3", 2021, genre.getId(), 16, 115, file.getId()));
        var result = sql2oFilmRepository.findAll();
        assertThat(result).isEqualTo(List.of(new FilmDto(film1, genre),
                new FilmDto(film2, genre),
                new FilmDto(film3, genre)));
    }

    @Test
    public void whenDontSaveThenNothingFount() {
        assertThat(sql2oFilmRepository.findAll()).isEqualTo(emptyList());
        assertThat(sql2oFilmRepository.findById(0)).isEqualTo(empty());
    }

    @Test
    public void whenDeleteThenGetEmptyOptional() {
        var film = sql2oFilmRepository.save(new Film("name", "description", 2020, genre.getId(), 12, 120, file.getId()));
        var isDeleted = sql2oFilmRepository.deleteById(film.getId());
        var savedFilm = sql2oFilmRepository.findById(film.getId());
        assertThat(isDeleted).isTrue();
        assertThat(savedFilm).isEqualTo(empty());
    }

    @Test
    public void whenDeleteByInvalidIdThenGetFalse() {
        assertThat(sql2oFilmRepository.deleteById(0)).isFalse();
    }

    @Test
    public void whenUpdateThenGetUpdated() {
        var film = sql2oFilmRepository.save(
                        new Film("name", "description", 2020,
                        genre.getId(), 12, 120, file.getId())
        );
        var updatedFilm = new Film("name", "new description", 2021,
                genre.getId(), 12, 120, file.getId());
        updatedFilm.setId(film.getId());
        var isUpdated = sql2oFilmRepository.update(updatedFilm);
        var savedFilm = sql2oFilmRepository.findById(film.getId());
        assertThat(isUpdated).isTrue();
        assertThat(new FilmDto(updatedFilm, genre)).usingRecursiveComparison().isEqualTo(savedFilm.get());
    }

    @Test
    public void whenUpdateNonexistentFilmThenGetFalse() {
        var updatedFilm = new Film("name", "new description", 2021,
                genre.getId(), 12, 120, file.getId());
        updatedFilm.setId(1);
        var isUpdated = sql2oFilmRepository.update(updatedFilm);
        assertThat(isUpdated).isFalse();
    }
}