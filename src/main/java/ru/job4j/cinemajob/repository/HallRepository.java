package ru.job4j.cinemajob.repository;

import ru.job4j.cinemajob.model.Hall;

import java.util.Collection;
import java.util.Optional;

public interface HallRepository {

    Hall save(Hall hall);

    Optional<Hall> findById(int id);

    Collection<Hall> findAll();

    boolean deleteById(int id);
}
