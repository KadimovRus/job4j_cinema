package ru.job4j.cinemajob.dto;

import ru.job4j.cinemajob.model.FilmSession;
import ru.job4j.cinemajob.model.Hall;
import ru.job4j.cinemajob.model.Ticket;
import ru.job4j.cinemajob.model.User;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public class TicketDto {

    public final static Map<String, String> COLUMNS_MAPPING = Map.of(
            "id", "id",
            "row_number", "rowNumber",
            "place_number", "placeNumber",
            "user_name", "userName",
            "hall_name", "hallName",
            "film_name", "filmName",
            "start_time", "startTime",
            "end_time", "endTime"
    );

    private int id;
    private int rowNumber;
    private int placeNumber;
    private String userName;
    private String hallName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public TicketDto() {

    }

    public TicketDto(Ticket ticket, User user, Hall hall, FilmSession filmSession) {
        this.id = ticket.getId();
        this.rowNumber = ticket.getRowNumber();
        this.placeNumber = ticket.getPlaceNumber();
        this.userName = user.getFullName();
        this.hallName = hall.getName();
        this.startTime = filmSession.getStartTime();
        this.endTime = filmSession.getEndTime();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TicketDto ticketDto = (TicketDto) o;
        return getId() == ticketDto.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "TicketDto{"
                + "id=" + id
                + ", rowNumber=" + rowNumber
                + ", placeNumber=" + placeNumber
                + ", userName='" + userName + '\''
                + ", startTime=" + startTime
                + ", endTime=" + endTime
                + '}';
    }

}
