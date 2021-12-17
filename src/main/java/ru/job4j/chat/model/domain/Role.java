package ru.job4j.chat.model.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

@Entity(name = "u_role")
@Table(name = "u_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        @PositiveOrZero(message = "id must not be negative")
    private int id;
    @Column(unique = true)
        @NotBlank(message = "role must not be empty")
    private String role;

    public static Role of(String role) {
        Role auth = new Role();
        auth.role = role;
        return auth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role authority = (Role) o;
        return id == authority.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Role{"
                + "id=" + id
                + ", role='" + role + '\''
                + '}';
    }
}