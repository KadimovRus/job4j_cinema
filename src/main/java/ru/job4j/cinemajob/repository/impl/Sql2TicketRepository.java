package ru.job4j.cinemajob.repository.impl;

import org.springframework.stereotype.Service;
import org.sql2o.Sql2o;
import ru.job4j.cinemajob.dto.TicketDto;
import ru.job4j.cinemajob.model.Ticket;
import ru.job4j.cinemajob.repository.TicketRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class Sql2TicketRepository implements TicketRepository {

    private final Sql2o sql2o;

    public Sql2TicketRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Ticket save(Ticket ticket) {
        try (var connection = sql2o.open()) {
            try (var query = connection.createQuery("INSERT INTO tickets (session_id, row_number, place_number, user_id) "
                    + "VALUES(:sessionId, :rowNumber, :placeNumber, :userId)")
                    .addParameter("sessionId", ticket.getSessionId())
                    .addParameter("rowNumber", ticket.getRowNumber())
                    .addParameter("placeNumber", ticket.getPlaceNumber())
                    .addParameter("userId", ticket.getUserId())) {
                int generatedId = query.executeUpdate().getKey(Integer.class);
                ticket.setId(generatedId);
                return ticket;
            }
        }
    }

    @Override
    public Optional<TicketDto> findById(int id) {
        var sql = """
                    SELECT t.id,
                    t.row_number,
                    t.place_number,
                    u.full_name as user_name,
                    h.name as hall_name,
                    fs.start_time,
                    fs.end_time
                    FROM tickets as t 
                    LEFT JOIN users as u ON t.user_id = u.id 
                    LEFT JOIN film_sessions as fs ON t.session_id = fs.id
                    LEFT JOIN halls as h ON fs.hall_id = h.id 
                    WHERE t.id = :id                    
                    """;
        try (var connection = sql2o.open();
             var query = connection.createQuery(sql).addParameter("id", id)) {
                var result = query.setColumnMappings(TicketDto.COLUMNS_MAPPING).executeAndFetchFirst(TicketDto.class);
                return Optional.ofNullable(result);
        }
    }

    @Override
    public Collection<TicketDto> findAll() {
        try (var connection = sql2o.open()) {
            var sql = """
                      SELECT t.id, t.row_number, t.place_number, u.full_name as user_name 
                      FROM tickets as t LEFT JOIN users as u ON t.user_id = u.id 
                      """;
            try (var query = connection.createQuery(sql)) {
                return query.setColumnMappings(TicketDto.COLUMNS_MAPPING).executeAndFetch(TicketDto.class);
            }
        }
    }

    @Override
    public Collection<TicketDto> findAllBySessionId(int sessionId) {
        var sql = """
                  SELECT t.id, t.row_number, t.place_number
                  FROM tickets as t WHERE t.session_id = :sessionId  
                  """;
        try (var connection = sql2o.open();
             var query = connection.createQuery(sql).addParameter("sessionId", sessionId)) {

            return query.setColumnMappings(TicketDto.COLUMNS_MAPPING).executeAndFetch(TicketDto.class);
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (var connection = sql2o.open();
             var query = connection.createQuery("DELETE FROM tickets WHERE id = :id")
                .addParameter("id", id)) {

            var affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public TicketDto findByRowAndPlaceAndSession(Integer row, Integer place, Integer sessionId) {
        var sql = """
                    SELECT
                    t.id, t.row_number, t.place_number
                    FROM tickets as t WHERE t.row_number = :row and t.place_number = :place and t.session_id = :sessionId
                    """;
        try (var connection = sql2o.open();
             var query = connection.createQuery(sql)
                            .addParameter("row", row)
                            .addParameter("place", place)
                            .addParameter("sessionId", sessionId)) {

            return query.setColumnMappings(TicketDto.COLUMNS_MAPPING).executeAndFetchFirst(TicketDto.class);
        }
    }
}
