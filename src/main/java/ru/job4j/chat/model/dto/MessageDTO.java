package ru.job4j.chat.model.dto;

import ru.job4j.chat.model.validator.Operation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MessageDTO {

    @NotNull(message = "Id must be non null", groups = {
            Operation.OnUpdate.class, Operation.OnDelete.class
    })
    private int id;
    @Size(min = 5, max = 1120, message
            = "Message must be between 5 and 1120 characters")
    private String message;
    @NotNull(message = "personId must be filled")
    private int personId;
    @NotNull(message = "roomId must be filled")
    private int roomId;

    public static MessageDTO of(int id, String message, int personId, int roomId) {
        MessageDTO m = new MessageDTO();
        m.setId(id);
        m.setMessage(message);
        m.setPersonId(personId);
        m.setRoomId(roomId);
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

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "Message{"
                + "id=" + id
                + ", message='" + message + '\''
                + ", personId=" + personId
                + ", roomId=" + roomId
                + '}';
    }
}
