package ru.job4j.cinemajob.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Hall {

    public final static Map<String, String> COLUMNS_MAPPING = Map.of(
        "id", "id",
        "name", "name",
        "row_count", "rowCount",
        "place_count", "placeCount",
        "description", "description"
    );

    private int id;
    private String name;
    private int rowCount;
    private int placeCount;
    private String description;

    public Hall(String name, int rowCount,
                int placeCount, String description) {
        this.name = name;
        this.rowCount = rowCount;
        this.placeCount = placeCount;
        this.description = description;
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

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getPlaceCount() {
        return placeCount;
    }

    public void setPlaceCount(int placeCount) {
        this.placeCount = placeCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hall hall = (Hall) o;
        return getId() == hall.getId() && Objects.equals(getName(), hall.getName()) && Objects.equals(getRowCount(), hall.getRowCount()) && Objects.equals(getPlaceCount(), hall.getPlaceCount()) && Objects.equals(getDescription(), hall.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getRowCount(), getPlaceCount(), getDescription());
    }

    @Override
    public String toString() {
        return "Hall{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", rowCount=" + rowCount
                + ", placeCount=" + placeCount
                + ", description='" + description + '\''
                + '}';
    }
}
