package ru.job4j.cinemajob.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinemajob.dto.FilmSessionDto;
import ru.job4j.cinemajob.model.FilmSession;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oFilmSessionRepository implements FilmSessionRepository {

    private final Sql2o sql2o;

    public Sql2oFilmSessionRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public FilmSession save(FilmSession filmSession) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(
                    "INSERT INTO film_sessions (film_id, hall_id, start_time, end_time, price) "
                            + "VALUES (:filmId, :hallId, :startTime, :endTime, :price)")
                    .addParameter("filmId", filmSession.getFilmId())
                    .addParameter("hallId", filmSession.getHallId())
                    .addParameter("startTime", filmSession.getStartTime())
                    .addParameter("endTime", filmSession.getEndTime())
                    .addParameter("price", filmSession.getPrice());
            int generatedId = query.setColumnMappings(FilmSession.COLUMNS_MAPPING)
                                   .executeUpdate().getKey(Integer.class);
            filmSession.setId(generatedId);
            return filmSession;
        }
    }

    @Override
    public Optional<FilmSessionDto> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("select "
                    + "fs.id, "
                    + "f.name as film_name, "
                    + "f.file_id, "
                    + "h.name as hall_name, "
                    + "fs.start_time, "
                    + "fs.end_time, "
                    + "fs.price "
                    + "from film_sessions as fs "
                    + "left join films as f on f.id = fs.film_id "
                    + "left join halls as h on h.id = hall_id "
                    + "where fs.id = :id");
            query.addParameter("id", id);
            var filmSessionDto = query.setColumnMappings(FilmSessionDto.COLUMN_MAPPING).executeAndFetchFirst(FilmSessionDto.class);
            return Optional.ofNullable(filmSessionDto);
        }
    }

    @Override
    public Collection<FilmSessionDto> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("select "
                    + "fs.id,"
                    + "f.name as film_name, "
                    + "f.file_id, "
                    + "h.name as hall_name, "
                    + "fs.start_time, "
                    + "fs.end_time, "
                    + "fs.price "
                    + "from film_sessions as fs "
                    + "left join films as f on f.id = fs.film_id "
                    + "left join halls as h on h.id = hall_id ");
            return query.setColumnMappings(FilmSessionDto.COLUMN_MAPPING).executeAndFetch(FilmSessionDto.class);
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM film_sessions WHERE id = :id")
                    .addParameter("id", id);
            var affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }
}
