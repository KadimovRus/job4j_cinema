package ru.job4j.cinemajob.dto;

import ru.job4j.cinemajob.model.Film;
import ru.job4j.cinemajob.model.FilmSession;
import ru.job4j.cinemajob.model.Hall;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public class FilmSessionDto {
    public static final Map<String, String> COLUMN_MAPPING = Map.of(
        "id", "id",
        "film_name", "filmName",
        "film_id", "filmId",
        "file_id", "fileId",
        "hall_name", "hallName",
        "start_time", "startTime",
        "end_time", "endTime",
        "row_count", "rowCount",
        "place_count", "placeCount",
        "price", "price"
    );

    private int id;
    private String filmName;
    private int fileId;
    private int filmId;
    private String hallName;
    private Integer rowCount;
    private Integer placeCount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int price;

    public FilmSessionDto(FilmSession filmSession, Film film, Hall hall) {
        this.id = filmSession.getId();
        this.filmName = film.getName();
        this.fileId = film.getFileId();
        this.hallName = hall.getName();
        this.rowCount = hall.getRowCount();
        this.placeCount = hall.getPlaceCount();
        this.startTime = filmSession.getStartTime();
        this.endTime = filmSession.getEndTime();
        this.price = filmSession.getPrice();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getRowCount() {
        return this.rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getPlaceCount() {
        return this.placeCount;
    }

    public void setPlaceCount(int placeCount) {
        this.placeCount = placeCount;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FilmSessionDto that = (FilmSessionDto) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "FilmSessionDto{"
                + "id=" + id
                + ", filmName='" + filmName + '\''
                + ", hallName='" + hallName + '\''
                + ", rowCount=" + this.rowCount
                + ", placeCount=" + this.placeCount
                + ", startTime=" + startTime
                + ", endTime=" + endTime
                + ", price=" + price
                + '}';
    }
}
