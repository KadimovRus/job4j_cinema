package ru.job4j.cinemajob.model;

import java.util.Map;
import java.util.Objects;

public class Ticket {

    public final static Map<String, String> COLUMNS_MAPPINGS = Map.of(
            "id", "id",
            "session_id", "sessionId",
            "row_number", "rowNumber",
            "place_number", "placeNumber",
            "hall_name", "hallName",
            "user_id", "user_id"
    );


    public Ticket(int sessionId, int rowNumber, int placeNumber, int userId) {
        this.sessionId = sessionId;
        this.rowNumber = rowNumber;
        this.placeNumber = placeNumber;
        this.userId = userId;
    }

    private int id;
    private int sessionId;
    private int rowNumber;
    private int placeNumber;
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return getId() == ticket.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Ticket{"
                + "id=" + id
                + ", sessionId=" + sessionId
                + ", rowNumber=" + rowNumber
                + ", placeNumber=" + placeNumber
                + ", userId=" + userId
                + '}';
    }
}
