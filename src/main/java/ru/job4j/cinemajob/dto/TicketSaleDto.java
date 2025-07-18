package ru.job4j.cinemajob.dto;

import java.util.Objects;

public class TicketSaleDto {

    private int rowNumber;
    private int placeNumber;
    private int sessionId;
    private int userId;

    public TicketSaleDto() {

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

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "TicketSaleDto{" +
                "rowNumber=" + rowNumber +
                ", placeNumber=" + placeNumber +
                ", sessionId=" + sessionId +
                ", userId=" + userId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketSaleDto that = (TicketSaleDto) o;
        return getRowNumber() == that.getRowNumber() && getPlaceNumber() == that.getPlaceNumber() && getSessionId() == that.getSessionId() && getUserId() == that.getUserId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRowNumber(), getPlaceNumber(), getSessionId(), getUserId());
    }
}
