package ru.job4j.chat.model.domain;

import ru.job4j.chat.model.validator.Operation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity(name = "message")
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        @NotNull(message = "Id must be non null", groups = {
            Operation.OnUpdate.class, Operation.OnDelete.class
        })
    private int id;
        @Size(min = 5, max = 1120, message
            = "Message must be between 5 and 1120 characters")
    private String message;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "person_id")
        @NotNull(message = "person must be non null", groups = {
            Operation.OnCreate.class, Operation.OnUpdate.class
        })
    private Person person;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "room_id")
        @NotNull(message = "room must be non null", groups = {
            Operation.OnCreate.class, Operation.OnUpdate.class
        })
    private Room room;

    public static Message of(int id, String message, Person person, Room room) {
        Message m = new Message();
        m.setId(id);
        m.setMessage(message);
        m.setPerson(person);
        m.setRoom(room);
        return m;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        return id == message.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Message{"
                + "id=" + id
                + ", message='" + message + '\''
                + ", person=" + person
                + ", room=" + room
                + '}';
    }
}
