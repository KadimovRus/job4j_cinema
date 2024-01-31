package ru.job4j.cinemajob.dto;

import ru.job4j.cinemajob.model.Film;
import ru.job4j.cinemajob.model.FilmSession;
import ru.job4j.cinemajob.model.Genre;
import ru.job4j.cinemajob.model.Hall;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FilmSessionDto {
    public static final Map<String, String> COLUMN_MAPPING = new HashMap<>();

    static {
        COLUMN_MAPPING.put("id", "id");
        COLUMN_MAPPING.put("film_name", "filmName");
        COLUMN_MAPPING.put("hall_name", "hallName");
        COLUMN_MAPPING.put("start_time", "startTime");
        COLUMN_MAPPING.put("end_time", "endTime");
        COLUMN_MAPPING.put("price", "price");
    }

    public FilmSessionDto(int id, Film film, Hall hall, LocalDateTime startTime, LocalDateTime endTime, int price) {
        this.id = id;
        this.filmName = film.getName();
        this.hallName = hall.getName();
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }

    private int id;
    private String filmName;
    private String hallName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int price;

    public FilmSessionDto() {
    }

    public FilmSessionDto(FilmSession filmSession, Film film, Hall hall) {
        this.id = filmSession.getId();
        this.filmName = film.getName();
        this.hallName = hall.getName();
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
                + ", startTime=" + startTime
                + ", endTime=" + endTime
                + ", price=" + price
                + '}';
    }
}
