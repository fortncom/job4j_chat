package ru.job4j.chat.model.domain;

import ru.job4j.chat.model.validator.Operation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity(name = "room")
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        @NotNull(message = "Id must be non null", groups = {
            Operation.OnUpdate.class, Operation.OnDelete.class
        })
    private int id;
    @Column(unique = true)
        @NotNull(message = "Name cannot be null")
        @Size(min = 2, max = 120, message
            = "Name must be between 2 and 120 characters")
    private String name;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "person_id")
        @NotNull(message = "ownerId must be filled", groups = {
            Operation.OnCreate.class, Operation.OnUpdate.class
        })
    private Person owner;

    public static Room of(int id, String name, Person owner) {
        Room room = new Room();
        room.setId(id);
        room.setName(name);
        room.setOwner(owner);
        return room;
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

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Room room = (Room) o;
        return id == room.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Room{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", owner=" + owner
                + '}';
    }
}
