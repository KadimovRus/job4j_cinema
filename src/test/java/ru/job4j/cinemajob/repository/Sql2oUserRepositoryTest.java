package ru.job4j.cinemajob.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinemajob.configuration.DatasourceConfiguration;
import ru.job4j.cinemajob.model.User;
import ru.job4j.cinemajob.repository.impl.Sql2oUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

public class Sql2oUserRepositoryTest {
    private static Sql2oUserRepository sql2oUserRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
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

        sql2oUserRepository = new Sql2oUserRepository(sql2o);
    }

    @AfterEach
    public void clearUsers() {
        var users = sql2oUserRepository.findAll();
        for (var user: users) {
            sql2oUserRepository.deleteById(user.getId());
        }
    }

    @Test
    void whenSaveThenGetSame() {
        var user = sql2oUserRepository.save(new User(0, "name", "email", "password")).get();
        var savedUser = sql2oUserRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()).get();
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void whenSaveSeveralThenGetAll() {
        var user1 = sql2oUserRepository.save(new User(0, "name", "email", "password")).get();
        var user2 = sql2oUserRepository.save(new User(0, "name1", "email1", "password1")).get();
        var user3 = sql2oUserRepository.save(new User(0, "name2", "email2", "password2")).get();
        var result = sql2oUserRepository.findAll();
        assertThat(result).isEqualTo(List.of(user1, user2, user3));
    }

    @Test
    void whenDontSaveThenNothingFound() {
        assertThat(sql2oUserRepository.findAll()).isEqualTo(emptyList());
        assertThat(sql2oUserRepository.findByEmailAndPassword("email", "password")).isEmpty();
    }

    @Test
    void whenSaveUserWithDuplicateEmailThenFail() {
        User user1 = new User(0, "user1", "user@gmail.com", "password");
        User user2 = new User(0, "user2", "user@gmail.com", "password");
        sql2oUserRepository.save(user1);
        Optional<User> duplicateUser = sql2oUserRepository.save(user2);
        assertThat(duplicateUser).isEmpty();
    }

    @Test
    void whenDeleteThenGetEmptyOptional() {
        var user = sql2oUserRepository.save(new User(
                0, "name", "email", "password")).get();
        var isDeleted = sql2oUserRepository.deleteById(user.getId());
        var savedUser = sql2oUserRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        assertThat(isDeleted).isTrue();
        assertThat(savedUser).isEmpty();
    }

    @Test
    void whenDeleteByInvalidIdThenGetFalse() {
        assertThat(sql2oUserRepository.deleteById(0)).isFalse();
    }
}