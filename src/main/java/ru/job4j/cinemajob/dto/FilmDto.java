package ru.job4j.cinemajob.dto;

import ru.job4j.cinemajob.model.Genre;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FilmDto {

    public static final Map<String, String> COLUMN_MAPPING = new HashMap<>();

    static {
        COLUMN_MAPPING.put("id", "id");
        COLUMN_MAPPING.put("name", "name");
        COLUMN_MAPPING.put("description", "description");
        COLUMN_MAPPING.put("release_year", "releaseYear");
        COLUMN_MAPPING.put("genre", "genre");
        COLUMN_MAPPING.put("minimal_age", "minimalAge");
        COLUMN_MAPPING.put("duration_in_minutes", "durationInMinutes");
    }

    private int id;
    private String name;
    private String description;
    private int releaseYear;
    private String genre;
    private int minimalAge;
    private int durationInMinutes;

    public FilmDto(int id, String name, String description,
                   int releaseYear, Genre genre, int minimalAge,
                   int durationInMinutes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseYear = releaseYear;
        this.genre = genre.getName();
        this.minimalAge = minimalAge;
        this.durationInMinutes = durationInMinutes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int year) {
        this.releaseYear = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genreName) {
        this.genre = genreName;
    }

    public int getMinimalAge() {
        return minimalAge;
    }

    public void setMinimalAge(int minimalAge) {
        this.minimalAge = minimalAge;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FilmDto filmDto = (FilmDto) o;
        return getId() == filmDto.getId() && getReleaseYear() == filmDto.getReleaseYear()
                && getMinimalAge() == filmDto.getMinimalAge()
                && getDurationInMinutes() == filmDto.getDurationInMinutes()
                && Objects.equals(getName(), filmDto.getName())
                && Objects.equals(getDescription(), filmDto.getDescription())
                && Objects.equals(getGenre(), filmDto.getGenre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(),
                getDescription(), getReleaseYear(),
                getGenre(), getMinimalAge(),
                getDurationInMinutes());
    }

    @Override
    public String toString() {
        return "FilmDto{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + ", year=" + releaseYear
                + ", genre='" + genre + '\''
                + ", minimalAge=" + minimalAge
                + ", durationInMinutes=" + durationInMinutes
                + '}';
    }
}
