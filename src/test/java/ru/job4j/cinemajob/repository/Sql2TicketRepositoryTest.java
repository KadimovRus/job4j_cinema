package ru.job4j.cinemajob.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinemajob.configuration.DatasourceConfiguration;
import ru.job4j.cinemajob.dto.TicketDto;
import ru.job4j.cinemajob.model.File;
import ru.job4j.cinemajob.model.Film;
import ru.job4j.cinemajob.model.FilmSession;
import ru.job4j.cinemajob.model.Genre;
import ru.job4j.cinemajob.model.Hall;
import ru.job4j.cinemajob.model.Ticket;
import ru.job4j.cinemajob.model.User;
import ru.job4j.cinemajob.repository.impl.Sql2TicketRepository;
import ru.job4j.cinemajob.repository.impl.Sql2oFileRepository;
import ru.job4j.cinemajob.repository.impl.Sql2oFilmRepository;
import ru.job4j.cinemajob.repository.impl.Sql2oFilmSessionRepository;
import ru.job4j.cinemajob.repository.impl.Sql2oGenreRepository;
import ru.job4j.cinemajob.repository.impl.Sql2oHallRepository;
import ru.job4j.cinemajob.repository.impl.Sql2oUserRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;

public class Sql2TicketRepositoryTest {

    private static Sql2TicketRepository sql2TicketRepository;
    private static Sql2oHallRepository sql2oHallRepository;
    private static Sql2oUserRepository sql2oUserRepository;
    private static Sql2oFilmSessionRepository sql2oFilmSessionRepository;
    private static Sql2oFilmRepository sql2oFilmRepository;
    private static Sql2oGenreRepository sql2oGenreRepository;
    private static Sql2oFileRepository sql2oFileRepository;
    private static Hall hall;
    private static User user;
    private static FilmSession filmSession;
    private static Film film;
    private static Genre genre;
    private static File file;

    @BeforeAll
    public static void initRepository() throws IOException {
        var properties = new Properties();
        try (var inputStream = Sql2oHallRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }

        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        var startTime = LocalDateTime.of(2023, 12, 1, 12, 0);
        var endTime = LocalDateTime.of(2023, 12, 1, 14, 0);

        sql2TicketRepository = new Sql2TicketRepository(sql2o);
        sql2oHallRepository = new Sql2oHallRepository(sql2o);
        sql2oUserRepository = new Sql2oUserRepository(sql2o);
        sql2oFilmSessionRepository = new Sql2oFilmSessionRepository(sql2o);
        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
        sql2oGenreRepository = new Sql2oGenreRepository(sql2o);
        sql2oFileRepository = new Sql2oFileRepository(sql2o);

        file = sql2oFileRepository.save(new File("test", "test"));
        hall = sql2oHallRepository.save(new Hall("name", 5, 5, "description"));
        user = sql2oUserRepository.save(new User(1, "User", "use@mail.ru", "12345")).get();
        genre = sql2oGenreRepository.save(new Genre(1, "Comedy"));
        film = sql2oFilmRepository.save(new Film("film", "interesting", 2023, genre.getId(), 14, 120, file.getId()));
        filmSession = sql2oFilmSessionRepository.save(new FilmSession(film.getId(), hall.getId(), startTime, endTime, 200));
    }

    @AfterEach
    public void clearTickets() {
        var tickets = sql2TicketRepository.findAll();
        for (TicketDto ticket: tickets) {
            sql2TicketRepository.deleteById(ticket.getId());
        }
    }

    @AfterAll
    public static void clear() {
        sql2oFilmSessionRepository.deleteById(filmSession.getId());
        sql2oFilmRepository.deleteById(film.getId());
        sql2oUserRepository.deleteById(user.getId());
        sql2oHallRepository.deleteById(hall.getId());
        sql2oGenreRepository.deleteById(genre.getId());
        sql2oFileRepository.deleteById(file.getId());
    }

    @Test
    void whenSaveThenGetSame() {
        var ticket = sql2TicketRepository.save(new Ticket(filmSession.getId(), 1, 1, user.getId()));
        var savedTicket = sql2TicketRepository.findById(ticket.getId());
        assertThat(savedTicket.get()).isEqualTo(new TicketDto(ticket, user, hall, filmSession));
    }

    @Test
    void whenSaveSeveralThenGetAll() {
        var ticket1 = sql2TicketRepository.save(new Ticket(filmSession.getId(), 1, 1, user.getId()));
        var ticket2 = sql2TicketRepository.save(new Ticket(filmSession.getId(), 2, 4, user.getId()));
        var ticket3 = sql2TicketRepository.save(new Ticket(filmSession.getId(), 3, 5, user.getId()));
        var result = sql2TicketRepository.findAll();
        assertThat(result).isEqualTo(List.of(new TicketDto(ticket1, user, hall, filmSession),
                                             new TicketDto(ticket2, user, hall, filmSession),
                                             new TicketDto(ticket3, user, hall, filmSession)));
    }

    @Test
    void whenDontSaveThenNothingFound() {
        assertThat(sql2TicketRepository.findAll()).isEqualTo(emptyList());
        assertThat(sql2TicketRepository.findById(0)).isEqualTo(empty());
    }

    @Test
    void whenDeleteThenGetEmptyOptional() {
        var ticket = sql2TicketRepository.save(new Ticket(filmSession.getId(), 2, 4, user.getId()));
        var isDeleted = sql2TicketRepository.deleteById(ticket.getId());
        var savedTicket = sql2TicketRepository.findById(ticket.getId());
        assertThat(isDeleted).isTrue();
        assertThat(savedTicket).isEqualTo(empty());
    }

    @Test
    void whenDeleteByInvalidIdThenGetFalse() {
        assertThat(sql2TicketRepository.deleteById(0)).isFalse();
    }

}