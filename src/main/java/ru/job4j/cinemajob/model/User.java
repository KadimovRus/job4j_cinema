package ru.job4j.cinemajob.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class User {

    public static final Map<String, String> COLUMN_MAPPING = new HashMap<>();

    static {
        COLUMN_MAPPING.put("id", "id");
        COLUMN_MAPPING.put("full_name", "fullName");
        COLUMN_MAPPING.put("email", "email");
        COLUMN_MAPPING.put("password", "password");
    }

    private int id;
    private String fullName;
    private String email;
    private String password;

    public User() {

    }

    public User(int id, String fullName, String email, String password) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return getId() == user.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
