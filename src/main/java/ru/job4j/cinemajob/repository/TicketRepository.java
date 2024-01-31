package ru.job4j.cinemajob.repository;

import ru.job4j.cinemajob.dto.TicketDto;
import ru.job4j.cinemajob.model.Ticket;

import java.util.Collection;
import java.util.Optional;

public interface TicketRepository {

    Ticket save(Ticket ticket);

    Optional<TicketDto> findById(int id);

    Collection<TicketDto> findAll();

    boolean deleteById(int id);
}
