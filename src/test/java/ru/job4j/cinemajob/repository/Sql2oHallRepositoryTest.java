package ru.job4j.cinemajob.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinemajob.configuration.DatasourceConfiguration;
import ru.job4j.cinemajob.model.Hall;
import ru.job4j.cinemajob.repository.impl.Sql2oHallRepository;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;

public class Sql2oHallRepositoryTest {

    private static Sql2oHallRepository sql2oHallRepository;

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

        sql2oHallRepository = new Sql2oHallRepository(sql2o);
    }

    @AfterEach
    public void clearHalls() {
        var halls = sql2oHallRepository.findAll();
        for (Hall hall: halls) {
            sql2oHallRepository.deleteById(hall.getId());
        }
    }

    @Test
    public void whenSaveThenGetSame() {
        var hall = sql2oHallRepository.save(new Hall("name", 5, 5, "description"));
        var savedHall = sql2oHallRepository.findById(hall.getId());
        assertThat(savedHall.get()).isEqualTo(hall);
    }

    @Test
    public void whenSaveSeveralThenGetAll() {
        var hall = sql2oHallRepository.save(new Hall("name", 5, 5, "description"));
        var hall2 = sql2oHallRepository.save(new Hall("name2", 15, 25, "description2"));
        var hall3 = sql2oHallRepository.save(new Hall("name3", 25, 35, "description3"));
        var result = sql2oHallRepository.findAll();
        assertThat(result).isEqualTo(List.of(hall, hall2, hall3));
    }

    @Test
    public void whenDontSaveThenGetNotNothing() {
        assertThat(sql2oHallRepository.findAll()).isEqualTo(emptyList());
        assertThat(sql2oHallRepository.findById(0)).isEqualTo(empty());
    }

    @Test
    public void whenDeleteThenGetEmptyOptional() {
        var hall = sql2oHallRepository.save(new Hall("name", 5, 5, "description"));
        var isDeleted = sql2oHallRepository.deleteById(hall.getId());
        var savedHall = sql2oHallRepository.findById(hall.getId());

        assertThat(isDeleted).isTrue();
        assertThat(savedHall).isEqualTo(empty());
    }

    @Test
    public void whenDeleteByInvalidIdThenGetFalse() {
        assertThat(sql2oHallRepository.deleteById(0)).isFalse();
    }
}