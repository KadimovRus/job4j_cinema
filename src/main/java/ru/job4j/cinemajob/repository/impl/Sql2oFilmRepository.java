package ru.job4j.cinemajob.repository.impl;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinemajob.dto.FilmDto;
import ru.job4j.cinemajob.model.Film;
import ru.job4j.cinemajob.repository.FilmRepository;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oFilmRepository implements FilmRepository {

    private final Sql2o sql2o;

    public Sql2oFilmRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Film save(Film film) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                    "INSERT INTO films (name, description, release_year, genre_id, minimal_age, duration_in_minutes, file_id) "
                            + "VALUES (:name, :description, :year, :genreId, :minimalAge, :durationInMinutes, :fileId)")
                    .addParameter("name", film.getName())
                    .addParameter("description", film.getDescription())
                    .addParameter("year", film.getReleaseYear())
                    .addParameter("genreId", film.getGenreId())
                    .addParameter("minimalAge", film.getMinimalAge())
                    .addParameter("durationInMinutes", film.getDurationInMinutes())
                    .addParameter("fileId", film.getFileId());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            film.setId(generatedId);
            return film;
        }
    }

    @Override
    public Optional<FilmDto> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                    "SELECT films.id, "
                            + "films.name, "
                            + "films.description, "
                            + "films.release_year, "
                            + "films.file_id, "
                            + "genres.name as genre, "
                            + "films.minimal_age, "
                            + "films.duration_in_minutes "
                            + "FROM films "
                            + "LEFT JOIN genres ON films.genre_id = genres.id "
                            + "WHERE films.id = :id")
                    .addParameter("id", id);
            var filmDto = query.setColumnMappings(FilmDto.COLUMN_MAPPING)
                               .executeAndFetchFirst(FilmDto.class);
            return Optional.ofNullable(filmDto);
        }
    }

    @Override
    public Collection<FilmDto> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                    "SELECT films.id, "
                            + "films.name, "
                            + "films.description, "
                            + "films.release_year, "
                            + "films.file_id, "
                            + "genres.name as genre, "
                            + "films.minimal_age, "
                            + "films.duration_in_minutes "
                            + "FROM films "
                            + "LEFT JOIN genres ON films.genre_id = genres.id");
            return query.setColumnMappings(FilmDto.COLUMN_MAPPING).executeAndFetch(FilmDto.class);
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM films WHERE id = :id")
                    .addParameter("id", id);
            var affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public boolean update(Film film) {
        try (var connection = sql2o.open()) {
            var sql = """
                    UPDATE films
                    SET name = :name, description = :description, release_year = :releaseYear,
                    genre_id = :genreId, minimal_age = :minimalAge, duration_in_minutes = :durationInMinutes,
                    file_id = :fileId
                    WHERE id = :id
                    """;
            var query = connection.createQuery(sql)
                    .addParameter("name", film.getName())
                    .addParameter("description", film.getDescription())
                    .addParameter("releaseYear", film.getReleaseYear())
                    .addParameter("genreId", film.getGenreId())
                    .addParameter("minimalAge", film.getMinimalAge())
                    .addParameter("durationInMinutes", film.getDurationInMinutes())
                    .addParameter("fileId", film.getFileId())
                    .addParameter("id", film.getId());
            var affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }
}
